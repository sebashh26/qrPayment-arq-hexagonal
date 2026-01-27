package com.mitocode.qrpayment.application.mapper;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.dto.PaymentDto;
import com.mitocode.qrpayment.domain.model.entity.Payment;

@Component
public class PaymentToPaymentDto {
	
	private PaymentToPaymentDto() {
		
	}
	
	public static PaymentDto buildPaymentDto(Payment payment) {
        PaymentDto response = new PaymentDto();
        response.setId(payment.getPaymentId());
        response.setQrCodeId(payment.getQrId());
        response.setWalletId(payment.getWalletId());
        response.setMerchantId(payment.getMerchantId());
        response.setPurchaseOrderNumber(payment.getOrder().getPurchaseOrderid());
        response.setAmount(payment.getOrder().getAmount());
        response.setCurrency(payment.getOrder().getCurrency());

        response.setStatus(payment.getStatus());
        response.setAuthorizedAt(payment.getAuthorizationInfo() != null ? payment.getAuthorizationInfo().getAuthorizedAt() : null);
        response.setRefundedAt(payment.getAuthorizationInfo() != null ? payment.getAuthorizationInfo().getRefundedAt() : null);
        return response;
    }

}
