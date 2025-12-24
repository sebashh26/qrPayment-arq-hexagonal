package com.mitocode.qrpayment.application.usecase.qr;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.command.CreateQRCommand;
import com.mitocode.qrpayment.application.dto.QRDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.QRCodeToQRDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.entity.QRCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.QRRepository;
import com.mitocode.qrpayment.domain.port.out.qr.QRGenerator;

@Component
public class CreateQRUseCase {
	
	private final QRRepository qrRepository;
	private final QRCodeToQRDto mapper;
	private final MerchantRepository merchantRepository;
	private final QRGenerator qrGenerate;
	
	public CreateQRUseCase(QRRepository qrRepository, QRCodeToQRDto mapper, QRGenerator qrGenerate, MerchantRepository merchantRepository) {
		this.qrRepository = qrRepository;
		this.mapper = mapper;
		this.merchantRepository = merchantRepository;
		this.qrGenerate = qrGenerate;
	}
	
	public QRDto execute(CreateQRCommand qrCmd) {	
		
		Optional<Merchant> merchantOpt = merchantRepository.findById(qrCmd.getMerchantId());
		if(merchantOpt.isEmpty()) {
			throw new BusinessException("Merchant does not exist");
		}
		if(!merchantOpt.get().isActive()) {
			throw new BusinessException("Merchant is not active");			
		}
		
		if(qrCmd.getType() == QRType.DYNAMIC) {
			if(qrCmd.getAmount() == null || qrCmd.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
				throw new BusinessException("Amount must be greater than zero for DYNAMIC QR");
			}
			if(qrCmd.getCurrencyCode() == null) {
				throw new BusinessException("CurrencyOrder is required for DYNAMIC QR");
			}
		}
		
		QRCode qrCode = new QRCode(
				qrCmd.getType(),
				qrCmd.getPurchaseOrder(),
				qrCmd.getMerchantId(),
				qrCmd.getExpirateDate(),				
				qrCmd.getCurrencyCode(),
				qrCmd.getAmount()
				);		
		
		byte[] qrImage = this.qrGenerate.generateImage(qrCode.getQrData());
        qrCode.setQrImage(qrImage);

        QRCode qrcodeResult = this.qrRepository.save(qrCode);
        return mapper.buildQRDto(qrcodeResult);
		
	}
	
	

}
