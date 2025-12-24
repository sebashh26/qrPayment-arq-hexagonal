package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.mitocode.qrpayment.domain.model.entity.QRCode;
import com.mitocode.qrpayment.domain.port.out.persistence.QRRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.QRCodeEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.QRCodeRepositoryJDBC;

@Repository
@Primary
public class QRCodeRepositoryJDBCAdapter implements QRRepository {

	private final QRCodeRepositoryJDBC jdbc;

    public QRCodeRepositoryJDBCAdapter(QRCodeRepositoryJDBC jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public QRCode save(QRCode qrCode) {
        return QRCodeEntityMapper.toDomain(jdbc.save(QRCodeEntityMapper.toEntity(qrCode)));
    }

    @Override
    public boolean existsQRCode(String qrId) {
        return jdbc.existsQRCode(qrId);
    }

    @Override
    public Optional<QRCode> findById(String qrId) {
        return jdbc.findById(qrId).map(QRCodeEntityMapper::toDomain);
    }

    @Override
    public QRCode update(QRCode qrCode) {
        return QRCodeEntityMapper.toDomain(jdbc.update(QRCodeEntityMapper.toEntity(qrCode)));
    }

}
