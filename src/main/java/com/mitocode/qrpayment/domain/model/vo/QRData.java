package com.mitocode.qrpayment.domain.model.vo;

import java.math.BigDecimal;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;

import lombok.Getter;

@Getter
public final class  QRData {
	
	private final String id;
	private final String merchantId;
	private final String purchaseOrder;
	private final CurrencyCode currencyCode;
	private final BigDecimal amount;
	private final QRType type;
	
	public QRData(String id, String merchantId, String purchaseOrder, CurrencyCode currencyCode, BigDecimal amount, QRType qrType) {
		this.id = id;
		this.merchantId = merchantId;
		this.purchaseOrder = purchaseOrder;
		this.currencyCode = currencyCode;
		this.amount = amount;
        this.type = qrType;
	}
	
	public static QRData from(String rawData) {
		
		try {
            String[] parts = rawData.split(":");
//            if (parts.length != 12 || !parts[0].equals("qr")) {
//                throw new IllegalArgumentException("Invalid QR data format");
//            }

            return new QRData(
                    parts[1],
                    parts[3],
                    parts[5],
                    CurrencyCode.valueOf(parts[9]),
                    new BigDecimal(parts[7]),
                    QRType.valueOf(parts[11])
            );
        } catch (Exception ex){
            throw new IllegalArgumentException("Failed to parse QR Data "+rawData);
        }
    }

}
