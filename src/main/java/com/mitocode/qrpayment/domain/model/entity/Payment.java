package com.mitocode.qrpayment.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.mitocode.qrpayment.domain.model.enums.BrandType;
import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

public class Payment {

	private String paymentId;
	private String merchantId;
	private String qrId;
	private BigDecimal amount;
	private CurrencyCode currency;
	private String purchaseOrderId;
	private PaymentStatus status;
	private BrandType brandType;
	private String walletId;
	private String failureReason;
	private LocalDateTime authorizedAt;
	private LocalDateTime refundedAt;

	public Payment(String merchantId, String qrId, BigDecimal amount, CurrencyCode currency, String purchaseOrderId,
			PaymentStatus status, BrandType brandType, String walletId, String failureReason,
			LocalDateTime authorizedAt, LocalDateTime refundedAt) {

		this.validateRequiredFields(merchantId, qrId, amount, currency, purchaseOrderId, status, brandType, walletId,
				authorizedAt);
		this.validateAmount(amount);

		this.paymentId = UUID.randomUUID().toString();
		this.merchantId = merchantId;
		this.qrId = qrId;
		this.amount = amount;
		this.currency = currency;
		this.purchaseOrderId = purchaseOrderId;
		this.status = status;
		this.brandType = brandType;
		this.walletId = walletId;
		this.failureReason = failureReason;
		this.authorizedAt = authorizedAt;
		this.refundedAt = refundedAt;

	}

	private void validateAmount(BigDecimal amount) {
		// TODO Auto-generated method stub
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}

	}

	private void validateRequiredFields(String merchantId, String qrId, BigDecimal amount, CurrencyCode currency,
			String purchaseOrderId, PaymentStatus status, BrandType brandType, String walletId,
			LocalDateTime authorizedAt) {
		
		if (merchantId == null || merchantId.isEmpty()) {
			throw new IllegalArgumentException("Merchant ID is required");
		}
		if (qrId == null || qrId.isEmpty()) {
			throw new IllegalArgumentException("QR ID is required");
		}
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required");
		}
		if (purchaseOrderId == null || purchaseOrderId.isEmpty()) {
			throw new IllegalArgumentException("Purchase Order ID is required");
		}
		if (status == null) {
			throw new IllegalArgumentException("Payment Status is required");
		}
		if (brandType == null) {
			throw new IllegalArgumentException("Brand Type is required");
		}
		if (walletId == null || walletId.isEmpty()) {
			throw new IllegalArgumentException("Wallet ID is required");
		}
		if (authorizedAt == null) {
			throw new IllegalArgumentException("Authorized At is required");
		}

	}
	
	public void refundPayment() {
		if(this.status != PaymentStatus.AUTHORIZED) {
			throw new IllegalStateException("Only authorized payments can be refunded");
		}
		this.status = PaymentStatus.REFUNDED;
		this.refundedAt = LocalDateTime.now();
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getQrId() {
		return qrId;
	}

	public void setQrId(String qrId) {
		this.qrId = qrId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public BrandType getBrandType() {
		return brandType;
	}

	public void setBrandType(BrandType brandType) {
		this.brandType = brandType;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public LocalDateTime getAuthorizedAt() {
		return authorizedAt;
	}

	public void setAuthorizedAt(LocalDateTime authorizedAt) {
		this.authorizedAt = authorizedAt;
	}

	public LocalDateTime getRefundedAt() {
		return refundedAt;
	}

	public void setRefundedAt(LocalDateTime refundedAt) {
		this.refundedAt = refundedAt;
	}

	public CurrencyCode getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyCode currency) {
		this.currency = currency;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

}
