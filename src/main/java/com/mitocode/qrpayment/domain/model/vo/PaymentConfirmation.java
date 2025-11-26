package com.mitocode.qrpayment.domain.model.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentConfirmation {
	
	private final String paymentId;
	private final String purchaseOrderId;
	private final BigDecimal amount;
	private final String currency;
	private final LocalDateTime authorizedAt;
	private final String status;
	
	public PaymentConfirmation(String paymentId, String purchaseOrderId, BigDecimal amount, String currency,
			LocalDateTime authorizedAt, String status) {
		this.paymentId = paymentId;
		this.purchaseOrderId = purchaseOrderId;
		this.amount = amount;
		this.currency = currency;
		this.authorizedAt = authorizedAt;
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public LocalDateTime getAuthorizedAt() {
		return authorizedAt;
	}

	public String getStatus() {
		return status;
	}
	
	
	
	
	
}
