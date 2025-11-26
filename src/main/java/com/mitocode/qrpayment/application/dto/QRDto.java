package com.mitocode.qrpayment.application.dto;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;

import java.math.BigDecimal;

//refereciado en el caso de uso CreateQRUseCase y en el mapper QRCodeToQRDto
public class QRDto {
    private String qrData;
    private String id;
    private BigDecimal amount;
    private CurrencyCode currency;
    private QRType type;
    private byte[] qrImage;

    public void setQrImage(byte[] qrImage) {
        this.qrImage = qrImage;
    }

    public void setQrData(String qrData) {
        this.qrData = qrData;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(QRType type) {
        this.type = type;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public String getQrData() {
        return qrData;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public QRType getType() {
        return type;
    }

    public byte[] getQrImage() {
        return qrImage;
    }
}
