package com.mitocode.qrpayment.domain.model.vo;

public class BrandAuthorizedRq {
	
	private String cardNumber;
	private String expirationMonth;
	private String expirationYear;
	private String cvv;
	
	
	public BrandAuthorizedRq(String cardNumber, String expirationMonth, String expirationYear, String cvv) {
		
		if (cardNumber == null || cardNumber.length() < 13) {
			throw new IllegalArgumentException("Invalid card number");
		}
		
		this.cardNumber = cardNumber;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.cvv = cvv;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}


}
