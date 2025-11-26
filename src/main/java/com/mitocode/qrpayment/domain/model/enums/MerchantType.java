package com.mitocode.qrpayment.domain.model.enums;

public enum MerchantType {

    PHYSICAL("Physical"),
    DIGITAL("Digital");

    private final String description;


    MerchantType(String description) {
        this.description=description;
    }

    public String getDescription(){
        return  this.description;
    }
    
    public boolean isDigital() {
		return this == DIGITAL;
	}
    
    
}
