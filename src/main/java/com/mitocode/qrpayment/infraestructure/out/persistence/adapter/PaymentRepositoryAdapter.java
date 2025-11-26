package com.mitocode.qrpayment.infraestructure.out.persistence.adapter;

import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.domain.port.out.persistence.PaymentRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.PaymentEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.PaymentEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.PaymentRepositoryJDBC;

public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentRepositoryJDBC jdbc;

    public PaymentRepositoryAdapter(PaymentRepositoryJDBC jdbc) {
        this.jdbc = jdbc;
    }

    //está diseñado así porque el repositorio guarda entidades, pero el servicio expone objetos de dominio.
    //Si devolvieras directamente PaymentEntity, estarías acoplando tu lógica de negocio a la base de datos,
    //lo cual rompe la arquitectura limpia.

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = jdbc.save(PaymentEntityMapper.toEntity(payment));
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
