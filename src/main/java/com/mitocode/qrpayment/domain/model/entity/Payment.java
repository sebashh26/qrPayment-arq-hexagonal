package com.mitocode.qrpayment.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

	private String paymentId;
    private String merchantId;
    private String qrId;
    private String walletId;
    private Card card;
    private Order order;
    private AuthorizationInfo authorizationInfo;
    private PaymentStatus status;

	public void validateAmount(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}

	}

	public void validateRequiredFields() {
		
		if (merchantId == null || merchantId.isEmpty()) {
			throw new IllegalArgumentException("Merchant ID is required");
		}
		if (qrId == null || qrId.isEmpty()) {
			throw new IllegalArgumentException("QR ID is required");
		}
		
		if (status == null) {
			throw new IllegalArgumentException("Payment Status is required");
		}
		
		if (walletId == null || walletId.isEmpty()) {
			throw new IllegalArgumentException("Wallet ID is required");
		}
		

	}
	
	public void refundPayment() {
		if(this.status != PaymentStatus.AUTHORIZED) {
			throw new IllegalStateException("Only authorized payments can be refunded");
		}
		this.status = PaymentStatus.REFUNDED;
		this.authorizationInfo.setRefundedAt(LocalDateTime.now());
	}
	
	public void generatePaymentId() {
        if (paymentId == null) {
            paymentId = UUID.randomUUID().toString();
        }
    }


}
