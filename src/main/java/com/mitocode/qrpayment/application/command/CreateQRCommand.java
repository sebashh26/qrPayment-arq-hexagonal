package com.mitocode.qrpayment.application.command;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateQRCommand {

	private String merchantId;
	private String purchaseOrder;
	private QRType type;
	private CurrencyCode currencyCode;
	private BigDecimal amount;
	private LocalDateTime expirateDate;

	public CreateQRCommand(String merchantId, String purchaseOrder, QRType type, CurrencyCode currencyCode,
			BigDecimal amount, LocalDateTime expirateDate) {
		this.merchantId = merchantId;
		this.purchaseOrder = purchaseOrder;
		this.type = type;
		this.currencyCode = currencyCode;
		this.amount = amount;
		this.expirateDate = expirateDate;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public QRType getType() {
		return type;
	}

	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDateTime getExpirateDate() {
		return expirateDate;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public void setType(QRType type) {
		this.type = type;
	}

	public void setCurrencyCode(CurrencyCode currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setExpirateDate(LocalDateTime expirateDate) {
		this.expirateDate = expirateDate;
	}
}
