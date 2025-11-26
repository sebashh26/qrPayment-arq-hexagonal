package com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment;

import java.math.BigDecimal;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;

public class PaymentRequest {
	
	private String merchantId;
    private String qrData;
    private BigDecimal amount;
    private CurrencyCode currency;
    private String purchaseOrderid;
    private String walletId;
    private String cardNumber;
    private String expirationMonth;
    private String expirationYear;
    private String cvv;

    public PaymentRequest(String merchantId, String qrData, BigDecimal amount, CurrencyCode currency, String purchaseOrderid, String walletId, String cardNumber, String expirationMonth, String expirationYear, String cvv) {
        this.merchantId = merchantId;
        this.qrData = qrData;
        this.amount = amount;
        this.currency = currency;
        this.purchaseOrderid = purchaseOrderid;
        this.walletId = walletId;
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
    }

    public PaymentRequest() {
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getQrData() {
        return qrData;
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

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

	public void setQrData(String qrData) {
		this.qrData = qrData;
	}

//	public PaymentStatus getPaymentStatus() {
//		return paymentStatus;
//	}
//
//
//	public void setPaymentStatus(PaymentStatus paymentStatus) {
//		this.paymentStatus = paymentStatus;
//	}
//	
	
	
	

}
