package com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment;

import java.math.BigDecimal;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRequest {
	
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
