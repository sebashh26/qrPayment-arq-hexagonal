package com.mitocode.qrpayment.domain.port.out.proxy;

import java.io.IOException;

import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;

public interface MerchantProxy {
	
	void confirmedPayment(PaymentConfirmation paymentConfirmation) throws IOException, InterruptedException;

}
