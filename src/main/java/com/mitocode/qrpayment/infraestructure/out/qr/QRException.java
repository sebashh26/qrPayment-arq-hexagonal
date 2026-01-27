package com.mitocode.qrpayment.infraestructure.out.qr;

public class QRException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QRException(String message) {
        super(message);
    }
}
