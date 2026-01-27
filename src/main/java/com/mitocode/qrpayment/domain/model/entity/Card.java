package com.mitocode.qrpayment.domain.model.entity;

import com.mitocode.qrpayment.domain.model.enums.BrandType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {
    private BrandType brand;
    private String pan;
    private String expirationMonth;
    private String expirationYear;
    private String cvc;
    private String cvcPresent;

    
    public void validateRequiredFields() {
        if (brand == null) {
            throw new IllegalStateException("BrandType is required");
        }
    }
    
}
