package com.mitocode.qrpayment.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRStatus;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.domain.model.exception.QRInvalidException;

public class QRCode {

	private String id;
	private String merchantId;
	private String purchaseOrder;
	private QRType type;
	private CurrencyCode currencyCode;
	private BigDecimal amount;
	private LocalDateTime expirateDate;
	private QRStatus status;
	private String qrData;
	private byte[] qrImage;

	public QRCode(String merchantId, String purchaseOrder, QRType qrType, CurrencyCode currencyOrder, BigDecimal amount,
			LocalDateTime expirateDate) {

		this.validateRequiredField(qrType, merchantId, purchaseOrder, currencyOrder, amount, expirateDate);
		// this.validateAmount(amount);
		this.validateExpirateDate(expirateDate);
		this.type = qrType;
		this.id = UUID.randomUUID().toString();
		this.merchantId = merchantId;
		this.purchaseOrder = purchaseOrder;
		this.currencyCode = currencyOrder;
		this.amount = amount;
		this.expirateDate = expirateDate;
		this.status = QRStatus.ACTIVE;

		this.gerenateQRData();
	}

	private void validateRequiredField(QRType type,String merchantId, String purchaseOrder, CurrencyCode currencyOrder,
			BigDecimal amount, LocalDateTime expirateDate) {
		// TODO Auto-generated m ethod stub
		if (merchantId == null || merchantId.isEmpty()) {
			throw new QRInvalidException("merchantId is required");
		}
		if (type == null) {
			throw new QRInvalidException("qrType is required");
		}

		// es una validacio q depende del flujo de negocio si qr es estaico o dinamico
		// si es dinamico el amount es requerido y tambien currency, es una validacion
		// de neocio
//		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
//			throw new QRInvalidException("amount must be greater than zero");
//		}
//		if (currencyOrder == null) {
//			throw new QRInvalidException("currencyOrder is required");
//		}
		if (expirateDate == null) {
			throw new QRInvalidException("expirateDate is required");
		}

		if (purchaseOrder == null || purchaseOrder.isEmpty()) {
			throw new QRInvalidException("purchaseOrder is required");
		}
	}

//	ya no iría porque depende del caso de uso y ya no es invarinate de la entidad 
//	private void validateAmount(BigDecimal amount) {
//		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
//			throw new QRInvalidException("amount must be greater than zero");
//		}
//	}

	private void validateExpirateDate(LocalDateTime expirateDate) {
		if (expirateDate.isBefore(LocalDateTime.now())) {
			throw new QRInvalidException("expirateDate must be a future date");
		}
	}

	// manejar el valor que va a tener el QR scaneado
	private void gerenateQRData() {
		// TODO Auto-generated method stub
//		this.qrData = QRData.from(
//				String.format("qr:%s:amount:%s:currencyCode:%s", this.currencyOrder, this.amount));
//		this.qrData = String.format("qr:%s:amount:%s:currency:%s:type:%s",
//                id, amount, currencyCode, type);
		this.qrData = String.format("qr:%s:merchantId:%s:purchaseOrder:%s:amount:%s:currency:%s:type:%s",
                id, merchantId,purchaseOrder ,amount, currencyCode, type );
	}

	public void isValidQR() {
		if (this.status != QRStatus.ACTIVE) {
			throw new QRInvalidException("QR Code is not active");
		}

		if (this.expirateDate.isBefore(LocalDateTime.now())) {
			throw new QRInvalidException("QR Code is expired");
		}

	}
	

	public boolean isActive() {
		return this.status == QRStatus.ACTIVE;		
	}

	public void setUsedQR() {
		this.status = QRStatus.USED;
	}

	public void setInactiveQR() {
		this.status = QRStatus.DISABLED;
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

	public QRType getQrtype() {
		return type;
	}

	public void setQrtype(QRType qrtype) {
		this.type = qrtype;
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

	public QRStatus getStatus() {
		return status;
	}

	public void setStatus(QRStatus status) {
		this.status = status;
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

}
