package com.mitocode.qrpayment.application.mapper;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;

@Component
public class MerchantToMerchantDto {

	public MerchantDto buildMerchantDto(Merchant merchant) {
        MerchantDto response = new MerchantDto();
        response.setMerchantId(merchant.getMerchantId());
        response.setName(merchant.getName());
        response.setEmail(merchant.getEmail());
        response.setStatus(merchant.getStatus());
        response.setCallbackUrl(merchant.getCallBackUrl());
        response.setType(merchant.getType());

        return response;

    }

}
