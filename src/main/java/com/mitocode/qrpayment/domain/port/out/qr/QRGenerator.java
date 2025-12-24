package com.mitocode.qrpayment.domain.port.out.qr;

import org.springframework.stereotype.Component;

@Component
public interface QRGenerator {
	
	byte[] generateImage (String qrData);

}
