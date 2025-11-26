package com.mitocode.qrpayment.domain.port.out.qr;

public interface QRGenerator {
	
	byte[] generateImage (String qrData);

}
