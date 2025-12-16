package com.mitocode.qrpayment.infraestructure.out.persistence.mapper;

import com.mitocode.qrpayment.domain.model.entity.QRCode;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.QRCodeEntity;

public class QRCodeEntityMapper {

	public static QRCodeEntity toEntity(QRCode qr) {
        QRCodeEntity entity = new QRCodeEntity();
        entity.setId(qr.getId());
        entity.setMerchantId(qr.getMerchantId());
        entity.setPurchaseOrder(qr.getPurchaseOrder());
        entity.setType(qr.getType());
        entity.setCurrencyCode(qr.getCurrencyCode());
        entity.setAmount(qr.getAmount());
        entity.setExpirateDate(qr.getExpirateDate());
        entity.setStatus(qr.getStatus());
        entity.setQrImage(qr.getQrImage());
        entity.setQrData(qr.getQrData());
        return entity;
    }

    public static QRCode toDomain(QRCodeEntity entity) {
        QRCode qr = new QRCode(
        		entity.getType(),
        		entity.getPurchaseOrder(),
        		entity.getMerchantId(),
        		entity.getExpirateDate(),
                entity.getCurrencyCode(),
                entity.getAmount()
        );
        qr.setId(entity.getId());
        qr.setQrImage(entity.getQrImage());
        qr.setQrData(entity.getQrData());
        return qr;
    }
}
