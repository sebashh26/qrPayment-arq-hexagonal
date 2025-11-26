package com.mitocode.qrpayment.domain.port.out.persistence;

import com.mitocode.qrpayment.domain.model.entity.Payment;

public interface PaymentRepository {
	
	Payment save(Payment payment);
    Payment findById(Integer id);
    Payment update(Payment payment);
	

}
