package com.mitocode.qrpayment.infraestructure.in.web.mapper;

import com.mitocode.qrpayment.application.command.CreateMerchantCommand;
import com.mitocode.qrpayment.application.command.UpdateMerchantCommand;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.CreateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.UpdateMerchantRequest;

public class MerchantRequestMapper {

    private MerchantRequestMapper() {}

    public static CreateMerchantCommand toCommand(CreateMerchantRequest request) {
        return new CreateMerchantCommand(
        		request.getEmail(),
        		request.getName(),
                request.getType(),
                request.getCallBackUrl()
        );
    }

    public static UpdateMerchantCommand toCommand(UpdateMerchantRequest request) {
        return new UpdateMerchantCommand(
                request.getMerchantId(),
                request.getName(),
                request.getType(),
                request.getCallBackUrl()
        );
    }
}
