package com.mitocode.qrpayment.domain.model.entity;

import java.math.BigDecimal;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	
	private String orderNumber;
    private CurrencyCode currency;
    private BigDecimal amount;
    private String purchaseOrderid;
    private String installments;//numero de cuotas

   
    public void validateRequiredFields() {
        if (amount == null) {
            throw new IllegalStateException("Amount is required");
        }
        if (currency == null) {
            throw new IllegalStateException("Currency is required");
        }
        if (purchaseOrderid == null) {
            throw new IllegalStateException("PurchaseOrderID is required");
        }
    }
    
}
