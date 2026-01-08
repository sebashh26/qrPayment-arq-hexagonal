package com.mitocode.qrpayment.application.usecase.authorize;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.command.PaymentCommand;
import com.mitocode.qrpayment.application.dto.PaymentDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.PaymentConfirmationMapper;
import com.mitocode.qrpayment.application.mapper.PaymentStatusMapper;
import com.mitocode.qrpayment.application.mapper.PaymentToPaymentDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.domain.model.entity.QRCode;
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
import com.mitocode.qrpayment.domain.port.out.persistence.WalletRepository;
import com.mitocode.qrpayment.domain.port.out.proxy.BrandProxy;
import com.mitocode.qrpayment.domain.port.out.proxy.MerchantProxy;

@Component
public class AuthorizedQRUseCase {

	private final MerchantProxy merchantProxy;
	private final MerchantRepository merchantRepository;
	private final QRRepository qrRepository;
	private final WalletRepository walletRepository;
	private final BrandProxy brandProxy;
	private final PaymentRepository paymentRepository;

	public AuthorizedQRUseCase(MerchantProxy merchantProxy, MerchantRepository merchantRepository,
			QRRepository qrRepository, WalletRepository walletRepository, BrandProxy brandProxy,
			PaymentRepository paymentRepository) {
		this.merchantProxy = merchantProxy;
		this.merchantRepository = merchantRepository;
		this.qrRepository = qrRepository;
		this.walletRepository = walletRepository;
		this.brandProxy = brandProxy;
		this.paymentRepository = paymentRepository;
	}

	public PaymentDto execute(PaymentCommand paymentCommand) {
		//USO  de form ejemplo:	Order.fromEvent(event) es un método de fábrica que transforma el DTO del evento en una entidad de dominio.
		QRData qrData = QRData.from(paymentCommand.getQrData());
		
		System.out.println(qrData.toString());
		
		Merchant merchant = merchantRepository.findById(paymentCommand.getMerchantId())
				.orElseThrow(() -> new RuntimeException("Merchant not found"));
		
		merchant.validate();
		
		QRCode qrCode = qrRepository.findById(qrData.getId())
				.orElseThrow(() -> new RuntimeException("QR Code not found"));
		
		qrCode.isValidQR();
		
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

        Payment payment = new Payment(paymentCommand.getMerchantId(),
        		qrData.getId(), paymentCommand.getAmount(),
                paymentCommand.getCurrency(), paymentCommand.getPurchaseOrderid(),
                PaymentStatusMapper.fromBrandStatus(brandAuthorizedRs.getStatus()),
                BrandType.fromCardNumber(paymentCommand.getCardNumber()),
                paymentCommand.getWalletId(),
                brandAuthorizedRs.getFailedMessage(),
                brandAuthorizedRs.getAuthorizedAt(), null
                );

        PaymentConfirmation confirmation = PaymentConfirmationMapper.toConfirmation(payment);

        try {
			merchantProxy.confirmedPayment(confirmation);
        } catch (IOException e) {
            throw new RuntimeException("I/O error notifying merchant: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restaurar estado de interrupción
            throw new RuntimeException("Thread interrupted while notifying merchant", e);
        }


        Payment paymentRs = paymentRepository.save(payment);
        return PaymentToPaymentDto.buildPaymentDto(paymentRs);

	}

}
