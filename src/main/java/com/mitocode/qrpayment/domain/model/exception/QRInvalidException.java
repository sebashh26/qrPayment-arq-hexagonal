package com.mitocode.qrpayment.domain.model.exception;

public class QRInvalidException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QRInvalidException(String message) {
		super(message);
	}

}
