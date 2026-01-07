package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.BrandType;
import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "payments")
public class PaymentEntity {
	
	@Id
	@Column(name = "paymentid", length = 36)
	private String paymentId;
	@Column(name = "merchantid", length = 36)
    private String merchantId;
	@Column(name = "qrid", length = 36)
    private String qrId;
	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	@Column(name = "currency", nullable = false, length = 20)
    private CurrencyCode currency;
	@Column(name = "purchaseorderid", nullable = false, length = 50)
    private String purchaseOrderid;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;
	@Enumerated(EnumType.STRING)
    @Column(name = "brandtype", nullable = false, length = 20)
    private BrandType brandType;
	@Column(name = "walletid", length = 36)
    private String walletId;
	@Column(name = "failurereason", length = 255)
    private String failureReason;
	@Column(name = "authorizedat")
    private LocalDateTime authorizedAt;
	@Column(name = "refundedat")
    private LocalDateTime refundedAt;

    public PaymentEntity(String paymentId, String merchantId, String qrId, BigDecimal amount, CurrencyCode currency, String purchaseOrderid, PaymentStatus status, BrandType brandType, String walletId, String failureReason, LocalDateTime authorizedAt, LocalDateTime refundedAt) {
        this.paymentId = paymentId;
        this.merchantId = merchantId;
        this.qrId = qrId;
        this.amount = amount;
        this.currency = currency;
        this.purchaseOrderid = purchaseOrderid;
        this.status = status;
        this.brandType = brandType;
        this.walletId = walletId;
        this.failureReason = failureReason;
        this.authorizedAt = authorizedAt;
        this.refundedAt = refundedAt;
    }

    public PaymentEntity() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public String getPurchaseOrderid() {
        return purchaseOrderid;
    }

    public void setPurchaseOrderid(String purchaseOrderid) {
        this.purchaseOrderid = purchaseOrderid;
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

}
