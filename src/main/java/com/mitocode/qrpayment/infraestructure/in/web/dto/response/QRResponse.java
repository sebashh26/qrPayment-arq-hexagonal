package com.mitocode.qrpayment.infraestructure.in.web.dto.response;

import java.math.BigDecimal;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;

public class QRResponse {

	private String qrData;
	private String id;
	private BigDecimal amount;
	private CurrencyCode currency;
	private QRType type;
	private byte[] qrImage;

	public QRResponse() {
		// TODO Auto-generated constructor stub
	}

	public QRResponse(String qrData, String id, BigDecimal amount, CurrencyCode currency, QRType type, byte[] qrImage) {
		this.qrData = qrData;
		this.id = id;
		this.amount = amount;
		this.currency = currency;
		this.type = type;
		this.qrImage = qrImage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getQrData() {
		return qrData;
	}

	public void setQrData(String qrData) {
		this.qrData = qrData;
	}

	public byte[] getQrImage() {
		return qrImage;
	}

	public void setQrImage(byte[] qrImage) {
		this.qrImage = qrImage;
	}

	public CurrencyCode getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyCode currency) {
		this.currency = currency;
	}

	public QRType getType() {
		return type;
	}

	public void setType(QRType type) {
		this.type = type;
	}

}
