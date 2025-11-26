package com.mitocode.qrpayment.domain.model.enums;

public enum MerchantStatus {
	
	ACTIVE("Active"),
	DISABLED("Disabled"),
	INACTIVE("Inactive");
	
	private final String status;

	MerchantStatus(String status) {
		this.status = status;
	}

	public String getSatus() {
		return this.status;
	}
	
}
