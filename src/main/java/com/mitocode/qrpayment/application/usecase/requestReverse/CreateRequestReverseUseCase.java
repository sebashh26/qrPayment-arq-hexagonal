package com.mitocode.qrpayment.application.usecase.requestReverse;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.entity.QRCode;
import com.mitocode.qrpayment.domain.model.entity.RequestReverse;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.QRRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.RequestReverseRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateRequestReverseUseCase {
	
	private final MerchantRepository merchantRepository;
    private final QRRepository qrRepository;
    private final RequestReverseRepository requestReverseRepository;
    
    public void execute(String qrId) {

        QRCode qrCode = qrRepository.findById(qrId)
                .orElseThrow(() -> new BusinessException("QR not found"));
        qrCode.isValidQR();

        Merchant merchant = merchantRepository.findById(qrCode.getMerchantId())
                .orElseThrow(() -> new BusinessException("Merchant not found"));
        merchant.validate();

        RequestReverse requestReverse = new RequestReverse
                (
                        qrCode.getMerchantId(),
                        qrCode.getId()
                );

        requestReverseRepository.create(requestReverse);


    }

}
