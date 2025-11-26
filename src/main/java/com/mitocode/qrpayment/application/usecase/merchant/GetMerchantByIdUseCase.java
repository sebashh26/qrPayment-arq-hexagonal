package com.mitocode.qrpayment.application.usecase.merchant;

import java.util.Optional;

import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

public class GetMerchantByIdUseCase {

	private final MerchantRepository merchantRepository;
    private final MerchantToMerchantDto mapper;


    public GetMerchantByIdUseCase(MerchantRepository merchantRepository,
                                 MerchantToMerchantDto mapper
    ) {
        this.merchantRepository = merchantRepository;
        this.mapper = mapper;
    }

    public MerchantDto execute(String  merchantId) {

        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);

        if (optionalMerchant.isEmpty()) {
            throw new BusinessException("Merchant not found");
        }

        return mapper.buildMerchantDto(optionalMerchant.get());

    }
}
