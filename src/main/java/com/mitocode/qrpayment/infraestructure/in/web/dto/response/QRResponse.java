package com.mitocode.qrpayment.infraestructure.in.web.dto.response;

import java.math.BigDecimal;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QRResponse {

	private String qrData;
	private String id;
	private BigDecimal amount;
	private CurrencyCode currency;
	private QRType type;
	private byte[] qrImage;

	
}
