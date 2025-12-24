package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.domain.port.out.persistence.PaymentRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.PaymentEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.PaymentEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa.PaymentRepositoryJPA;

@Component
public class PaymentRepositoryJPAAdapter implements PaymentRepository {
	
	@Autowired
	private PaymentRepositoryJPA paymentRepositoryJPA;

	@Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = paymentRepositoryJPA.save(PaymentEntityMapper.toEntity(payment));
        return PaymentEntityMapper.toDomain(paymentEntity);
    }

    @Override
    public Payment findById(Integer id) {
        return null;
    }

    @Override
    public Payment update(Payment payment) {
        return null;
    }

}
