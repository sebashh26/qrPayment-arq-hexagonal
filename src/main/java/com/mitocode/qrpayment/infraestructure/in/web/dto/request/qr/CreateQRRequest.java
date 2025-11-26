package com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;

public class CreateQRRequest {

	private String merchantId;
	private String purchaseOrder;
	private QRType qrtype;
	private CurrencyCode currencyCode;
	private BigDecimal amount;
	private LocalDateTime expirateDate;
	
	public CreateQRRequest() {}

	// VALIDACIONES ESTRUCTURALES: validaciones que son nuestra primera linea de
	// defensa
	public CreateQRRequest(String merchantId, String purchaseOrder, QRType qrtype, CurrencyCode currencyOrder,
			BigDecimal amount, LocalDateTime expirateDate) {

		this.validateRequiredField(merchantId, purchaseOrder, qrtype, currencyOrder, amount, expirateDate);
		this.validateExpirateDate(expirateDate);
		this.merchantId = merchantId;
		this.purchaseOrder = purchaseOrder;
		this.qrtype = qrtype;
		this.currencyCode = currencyOrder;
		this.amount = amount;
		this.expirateDate = expirateDate;
	}

	private void validateRequiredField(String merchantId, String purchaseOrder, QRType qrType,
			CurrencyCode currencyOrder, BigDecimal amount, LocalDateTime expirateDate) {
		// TODO Auto-generated m ethod stub
		if (merchantId == null || merchantId.isEmpty()) {
			throw new IllegalArgumentException("merchantId is required");
		}
		if (purchaseOrder == null || purchaseOrder.isEmpty()) {
			throw new IllegalArgumentException("purchaseOrder is required");
		}

		if (qrtype == null) {
			throw new IllegalArgumentException("qrType is required");
		}

		//esta validacio q depende del flujo de negocio si qr es estaico o dinamico si es dinamico el amount es requerido y tambien currency, es una validacion de negocio
//		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
//			throw new IllegalArgumentException("amount must be greater than zero");
//		}
//
//		if (currencyOrder == null) {
//			throw new IllegalArgumentException("currencyOrder is required");
//		}

		if (expirateDate == null) {
			throw new IllegalArgumentException("expirateDate is required");
		}
	}

	private void validateExpirateDate(LocalDateTime expirateDate) {
		if (expirateDate.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("expirateDate must be a future date");
		}
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public QRType getQrtype() {
		return qrtype;
	}

	public void setQrtype(QRType qrtype) {
		this.qrtype = qrtype;
	}

	public CurrencyCode getCurrencyOrder() {
		return currencyCode;
	}

	public void setCurrencyOrder(CurrencyCode currencyOrder) {
		this.currencyCode = currencyOrder;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getExpirateDate() {
		return expirateDate;
	}

	public void setExpirateDate(LocalDateTime expirateDate) {
		this.expirateDate = expirateDate;
	}

	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(CurrencyCode currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
}
