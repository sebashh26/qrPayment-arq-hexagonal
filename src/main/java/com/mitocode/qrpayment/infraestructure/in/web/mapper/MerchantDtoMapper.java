package com.mitocode.qrpayment.infraestructure.in.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.MerchantResponse;

public class MerchantDtoMapper {

    private MerchantDtoMapper() {}

    public static MerchantResponse toResponse(MerchantDto dto) {
        MerchantResponse response = new MerchantResponse();
        response.setMerchantId(dto.getMerchantId());
        response.setName(dto.getName());
        response.setEmail(dto.getEmail());
        response.setType(dto.getType());
        response.setCallBackUrl(dto.getCallbackUrl());
        response.setStatus(dto.getStatus());
        return response;
    }

    public static List<MerchantResponse> toResponseList(List<MerchantDto> dtoList) {
        return dtoList.stream()
                .map(MerchantDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
