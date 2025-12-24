package com.mitocode.qrpayment.application.usecase.merchant;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.command.UpdateMerchantCommand;
import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.exception.MerchantInvalidateException;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

@Component
public class UpdateMerchantUseCase {

	private final MerchantRepository merchantRepository;
    private final MerchantToMerchantDto mapper;


    public UpdateMerchantUseCase(MerchantRepository merchantRepository,
                                 MerchantToMerchantDto mapper
    ) {
        this.merchantRepository = merchantRepository;
        this.mapper = mapper;
    }

    public MerchantDto execute(UpdateMerchantCommand merchantCmd) {

        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantCmd.getMerchantId());

        if (optionalMerchant.isEmpty()) {
            throw new BusinessException("Merchant not found");
        }

        Merchant merchant  = optionalMerchant.get();

        if (merchantCmd.getType() == null) {
        	throw new MerchantInvalidateException("type is required");
        }

        String callbackUrl = merchantCmd.getCallbackUrl();

        if (merchantCmd.getType().isDigital()) {
            if (callbackUrl == null || callbackUrl.isEmpty()) {
                throw new BusinessException("callbackUrl is required");
            }
        }

        merchant.setCallBackUrl(callbackUrl);
        merchant.setType(merchantCmd.getType());
        

        if (merchantCmd.getName() != null && !merchantCmd.getName().isEmpty()) {
            merchant.setName(merchantCmd.getName());
        }

        Merchant merchantUpdated  = merchantRepository.update(merchant);

        return mapper.buildMerchantDto(merchantUpdated);

    }
}
