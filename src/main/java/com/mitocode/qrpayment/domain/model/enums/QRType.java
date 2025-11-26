package com.mitocode.qrpayment.domain.model.enums;

public enum QRType {
	
	DYNAMIC("Dynamic"),
	STATIC("Static");
	
	private final String type;
	
	 QRType(String type) {
		this.type = type;
	}	
	
	 public String getType() {
		 return this.type;
	 }

}
