package com.mitocode.qrpayment.domain.port.out.proxy;

import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;

public interface MerchantProxy {
	
	void confirmedPayment(PaymentConfirmation paymentConfirmation) ;

}
