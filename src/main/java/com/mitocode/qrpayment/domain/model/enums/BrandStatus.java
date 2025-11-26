package com.mitocode.qrpayment.domain.model.enums;

public enum BrandStatus {
	
	AUTHORIZED("Authorized"),
	PENDING("Pending"),
	DENIED("Denied");
	
	private String description;
	
	private BrandStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
