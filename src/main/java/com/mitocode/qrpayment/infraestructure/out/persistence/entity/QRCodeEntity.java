package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRStatus;
import com.mitocode.qrpayment.domain.model.enums.QRType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "qrcodes")
public class QRCodeEntity {

	@Id
	@Column(name = "qrid")
	private String id;
	@Column(name = "merchantid", length = 36)
	private String merchantId;
	@Column(name = "purchaseordernumber", nullable = false, length = 50)
	private String purchaseOrder;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 20)
	private QRType type;
	@Enumerated(EnumType.STRING)
	@Column(name = "currencycode", nullable = false, length = 20)
	private CurrencyCode currencyCode;
	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	@Column(name = "expiratedate")
	private LocalDateTime expirateDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private QRStatus status;
	@Column(name = "qrimage")
	private byte[] qrImage;
	@Column(name = "qrdata")
	private String qrData;

	public QRCodeEntity() {
	}

	public QRCodeEntity(String id, String merchantId, String purchaseOrder, QRType type, CurrencyCode currencyCode,
			BigDecimal amount, LocalDateTime expirateDate, QRStatus status, byte[] qrImage, String qrData) {
		this.id = id;
		this.merchantId = merchantId;
		this.purchaseOrder = purchaseOrder;
		this.type = type;
		this.currencyCode = currencyCode;
		this.amount = amount;
		this.expirateDate = expirateDate;
		this.status = status;
		this.qrImage = qrImage;
		this.qrData = qrData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public QRType getType() {
		return type;
	}

	public void setType(QRType type) {
		this.type = type;
	}

	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(CurrencyCode currencyCode) {
		this.currencyCode = currencyCode;
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

	public QRStatus getStatus() {
		return status;
	}

	public void setStatus(QRStatus status) {
		this.status = status;
	}

	public byte[] getQrImage() {
		return qrImage;
	}

	public void setQrImage(byte[] qrImage) {
		this.qrImage = qrImage;
	}

	public String getQrData() {
		return qrData;
	}

	public void setQrData(String qrData) {
		this.qrData = qrData;
	}

	@Override
	public String toString() {
		return "QRCodeEntity{" + "id='" + id + '\'' + ", merchantId='" + merchantId + '\'' + ", purchaseOrder='"
				+ purchaseOrder + '\'' + ", type=" + type + ", currencyCode=" + currencyCode + ", amount=" + amount
				+ ", expirateDate=" + expirateDate + ", status=" + status + ", qrImage=" + Arrays.toString(qrImage)
				+ ", qrData='" + qrData + '\'' + '}';
	}

}
