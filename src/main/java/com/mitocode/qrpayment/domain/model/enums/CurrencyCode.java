package com.mitocode.qrpayment.domain.model.enums;

public enum CurrencyCode {
	
	PEN("Soles"),
	USD("Dollars");
	
	private final String currency;
	
	 CurrencyCode(String currency) {
		this.currency = currency;
	}	
	 
	 public String getCurrencyCode() {
		 return this.currency;
	 }				
	 
	 
	

}
