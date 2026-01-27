package com.mitocode.qrpayment.application.command;

import java.math.BigDecimal;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;

import lombok.AllArgsConstructor;
import lombok.Data;


//Los objetos de tipos  command se usan  para iniciar o traer objetos sin reglas de negocio a los procesos UseCase
@Data
@AllArgsConstructor
public class PaymentCommand {

	private String merchantId;
	private String qrData;
	private BigDecimal amount;
	private CurrencyCode currency;
	private String purchaseOrderid;
	private String walletId;
	private String cardNumber;
	private String expirationMonth;
	private String expirationYear;
	private String cvv;
	
}
