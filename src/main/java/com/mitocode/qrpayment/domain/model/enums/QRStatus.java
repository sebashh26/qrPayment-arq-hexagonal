package com.mitocode.qrpayment.domain.model.enums;

public enum QRStatus {

	ACTIVE("Active"),
	INACTIVE("Inactive"),
	USED("Used"),
	EXPIRED("Expired");
	
	private final String status;

	QRStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
