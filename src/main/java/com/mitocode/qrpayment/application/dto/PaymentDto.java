package com.mitocode.qrpayment.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

public class PaymentDto {

	private String id;
    private String qrCodeId;
    private String walletId;
    private String merchantId;
    private String purchaseOrderNumber;
    private BigDecimal amount;
    private CurrencyCode currency;
    private PaymentStatus status;
    private LocalDateTime authorizedAt;
    private LocalDateTime refundedAt;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQrCodeId() {
		return qrCodeId;
	}
	public void setQrCodeId(String qrCodeId) {
		this.qrCodeId = qrCodeId;
	}
	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public CurrencyCode getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyCode currency) {
		this.currency = currency;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
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
    
    
}
