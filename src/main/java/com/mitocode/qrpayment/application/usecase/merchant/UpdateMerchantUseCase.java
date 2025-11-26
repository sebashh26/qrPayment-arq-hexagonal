package com.mitocode.qrpayment.application.usecase.merchant;

import java.util.Optional;

import com.mitocode.qrpayment.application.command.UpdateMerchantCommand;
import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

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

        MerchantType merchantTypeFlux = merchant.getType();

        if (merchantCmd.getType() != null) {
            merchantTypeFlux = merchantCmd.getType();
        }

        if (merchantCmd.getCallbackUrl() != null && !merchantCmd.getCallbackUrl().isEmpty()) {
            if (merchantTypeFlux.isDigital() ) {
                if (merchantCmd.getCallbackUrl() == null || merchantCmd.getCallbackUrl().isEmpty()) {
                    throw  new BusinessException("callbackUrl is required");
                }
            }
            merchant.setCallBackUrl(merchantCmd.getCallbackUrl());
        }

        if (merchantCmd.getType() != null) {
            merchant.setType(merchantCmd.getType());
        }

        if (merchantCmd.getName() != null && !merchantCmd.getName().isEmpty()) {
            merchant.setName(merchantCmd.getName());
        }

        Merchant merchantUpdated  = merchantRepository.update(merchant);

        return mapper.buildMerchantDto(merchantUpdated);

    }
}
