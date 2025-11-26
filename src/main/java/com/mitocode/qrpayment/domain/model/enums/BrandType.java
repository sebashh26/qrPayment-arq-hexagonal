package com.mitocode.qrpayment.domain.model.enums;

public enum BrandType {
	
	VISA("Visa"),
	MASTERCARD("Mastercard"),
	AMEX("Amex");
	
	private final String description;
	
	BrandType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static BrandType fromCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid card number");
        }

        if (cardNumber.startsWith("4")) {
            return VISA;
        } else if (cardNumber.matches("^5[1-5].*") || cardNumber.matches("^2(2[2-9]|[3-6]|7[01]).*")) {
            return MASTERCARD;
        } else if (cardNumber.matches("^3[47].*")) {
            return AMEX;
        } else {
            throw new IllegalArgumentException("Unknown card brand");
        }
    }
	
	

}
