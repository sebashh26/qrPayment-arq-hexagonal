package com.mitocode.qrpayment.application.usecase.authorize;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.command.PaymentCommand;
import com.mitocode.qrpayment.application.dto.PaymentDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.PaymentConfirmationMapper;
import com.mitocode.qrpayment.application.mapper.PaymentStatusMapper;
import com.mitocode.qrpayment.application.mapper.PaymentToPaymentDto;
import com.mitocode.qrpayment.domain.model.entity.AuthorizationInfo;
import com.mitocode.qrpayment.domain.model.entity.Card;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.entity.Order;
import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.domain.model.entity.QRCode;
import com.mitocode.qrpayment.domain.model.entity.RequestReverse;
import com.mitocode.qrpayment.domain.model.entity.Wallet;
import com.mitocode.qrpayment.domain.model.enums.BrandType;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizationResult;
import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizedRq;
import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;
import com.mitocode.qrpayment.domain.model.vo.QRData;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.PaymentRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.QRRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.RequestReverseRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.WalletRepository;
import com.mitocode.qrpayment.domain.port.out.proxy.BrandProxy;
import com.mitocode.qrpayment.domain.port.out.proxy.MerchantProxy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AuthorizedQRUseCase {

	private final MerchantProxy merchantProxy;
	private final MerchantRepository merchantRepository;
	private final QRRepository qrRepository;
	private final WalletRepository walletRepository;
	private final BrandProxy brandProxy;
	private final PaymentRepository paymentRepository;
	
	private final RequestReverseRepository requestReverseRepository;

	//USE CASE: Orquestan reglas de negocio: coordinan entidades y lógica del dominio para cumplir un objetivo (ej. crear orden, procesar pago).

	public PaymentDto execute(PaymentCommand paymentCommand) {
		//USO  de form ejemplo:	Order.fromEvent(event) es un método de fábrica que transforma el DTO del evento en una entidad de dominio.
		QRData qrData = QRData.from(paymentCommand.getQrData());
		
		//System.out.println(qrData.toString());
		
		Merchant merchant = merchantRepository.findById(paymentCommand.getMerchantId())
				.orElseThrow(() -> new BusinessException("Merchant not found"));
		
		merchant.validate();
		
		QRCode qrCode = qrRepository.findById(qrData.getId())
				.orElseThrow(() -> new BusinessException("QR Code not found"));
		
		qrCode.isValidQR();
		
		Optional<RequestReverse> requestReverse =  requestReverseRepository.findByMerchantIdAndQrId(merchant.getMerchantId(), qrCode.getId());
		requestReverse.ifPresent(r -> {
			throw new BusinessException("The transaction have a request reverse pending");
		});
		
		if (qrCode.getQrtype() == QRType.DYNAMIC){
            if (qrCode.getAmount().compareTo(paymentCommand.getAmount()) != 0) {
                throw new BusinessException("Amount doesn't match");
            }
            if (!qrCode.getCurrencyOrder().equals(paymentCommand.getCurrency())) {
                throw new BusinessException("Currency code doesn't match");
            }
        }

		 //¿Por qué no lo recibe como Optional?
		//		 Porque tú mismo estás diciendo:
		//		 - “Si existe, dame el objeto. Si no existe, lanza excepción.”
//		- Tu repositorio devuelve Optional<Wallet> para representar ausencia/presencia.
//		- Tu servicio usa .orElseThrow para convertir ese Optional en un Wallet o en una excepción.
//		- Por eso la variable final wallet ya no es Optional, sino el objeto concreto.//
        Wallet wallet = walletRepository.findById(paymentCommand.getWalletId())
                .orElseThrow(() -> new BusinessException("Wallet not found"));
        wallet.validate();

        BrandAuthorizedRq brandRequest = new BrandAuthorizedRq(
                paymentCommand.getCardNumber(),
                paymentCommand.getExpirationMonth(),
                paymentCommand.getExpirationYear(),
                paymentCommand.getCvv()
        );	

        BrandAuthorizationResult brandAuthorizedRs = brandProxy.authorizePayment(brandRequest);
        
        Card card =  Card.builder().brand(BrandType.fromCardNumber(paymentCommand.getCardNumber()))
				.build();
        card.validateRequiredFields();
        
        Order order =  Order.builder().orderNumber(generateOrderNumber()).currency(paymentCommand.getCurrency()).
        						amount(paymentCommand.getAmount()).purchaseOrderid(paymentCommand.getPurchaseOrderid()).
        						installments("1").build();
        order.validateRequiredFields();
        
        AuthorizationInfo authInfo = AuthorizationInfo.builder()
				.authorizedAt(brandAuthorizedRs.getAuthorizedAt())
				.failureReason(brandAuthorizedRs.getFailedMessage())
				.build();
        
        Payment payment = Payment.builder()
        		.merchantId(paymentCommand.getMerchantId())
        		.qrId(qrData.getId())
        		.walletId(paymentCommand.getWalletId()).card(card).order(order)
        		.authorizationInfo(authInfo)
				.status(PaymentStatusMapper.fromBrandStatus(brandAuthorizedRs.getStatus()))
				.build();
        
        payment.validateRequiredFields();
        payment.validateAmount(paymentCommand.getAmount());
        payment.generatePaymentId();
        
        PaymentConfirmation confirmation = PaymentConfirmationMapper.toConfirmation(payment);
        
		merchantProxy.confirmedPayment(confirmation);

        Payment paymentRs = paymentRepository.save(payment);
        return PaymentToPaymentDto.buildPaymentDto(paymentRs);

	}
	
	private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
