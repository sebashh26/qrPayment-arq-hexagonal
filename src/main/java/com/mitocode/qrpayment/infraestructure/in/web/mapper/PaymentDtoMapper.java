package com.mitocode.qrpayment.infraestructure.in.web.mapper;

import com.mitocode.qrpayment.application.dto.PaymentDto;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.PaymentResponse;

public class PaymentDtoMapper {
	
	private PaymentDtoMapper() {}

    public static PaymentResponse toResponse(PaymentDto payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setQrCodeId(payment.getQrCodeId());
        response.setWalletId(payment.getWalletId());
        response.setMerchantId(payment.getMerchantId());
        response.setPurchaseOrderNumber(payment.getPurchaseOrderNumber());
        response.setAmount(payment.getAmount());
        response.setCurrency(payment.getCurrency());

        response.setStatus(payment.getStatus());
        response.setAuthorizedAt(payment.getAuthorizedAt());
        response.setRefundedAt(payment.getRefundedAt());
        return response;
    }

}
