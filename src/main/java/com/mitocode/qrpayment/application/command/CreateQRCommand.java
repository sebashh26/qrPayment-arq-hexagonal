package com.mitocode.qrpayment.application.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQRCommand {

	private String merchantId;
	private String purchaseOrder;
	private QRType type;
	private CurrencyCode currencyCode;
	private BigDecimal amount;
	private LocalDateTime expirateDate;

}
