package com.mitocode.qrpayment.domain.model.enums;

public enum PaymentStatus {
	
	AUTHORIZED("Authorized"),
	DENIED("Denied"),
	REFUNDED("Refunded");
	
	private final String description;
	
	PaymentStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
