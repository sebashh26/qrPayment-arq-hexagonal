package com.mitocode.qrpayment.application.mapper;

import com.mitocode.qrpayment.application.dto.PaymentDto;
import com.mitocode.qrpayment.domain.model.entity.Payment;

public class PaymentToPaymentDto {
	
	public static PaymentDto buildPaymentDto(Payment payment) {
        PaymentDto response = new PaymentDto();
        response.setId(payment.getPaymentId());
        response.setQrCodeId(payment.getQrId());
        response.setWalletId(payment.getWalletId());
        response.setMerchantId(payment.getMerchantId());
        response.setPurchaseOrderNumber(payment.getPurchaseOrderId());
        response.setAmount(payment.getAmount());
        response.setCurrency(payment.getCurrency());

        response.setStatus(payment.getStatus());
        response.setAuthorizedAt(payment.getAuthorizedAt());
        response.setRefundedAt(payment.getRefundedAt());
        return response;
    }

}
