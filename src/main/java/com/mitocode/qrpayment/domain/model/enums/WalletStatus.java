package com.mitocode.qrpayment.domain.model.enums;

public enum WalletStatus {
	
	ACTIVE("Active"),
	INACTIVE("Inactive");
	
	private String description;
	
	private WalletStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	
}
