package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.domain.model.entity.QRCode;
import com.mitocode.qrpayment.domain.port.out.persistence.QRRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.QRCodeEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa.QRCodeRepositoryJPA;

@Component
public class QRCodeRepositoryJPAAdapter implements QRRepository {
	
	@Autowired
	private QRCodeRepositoryJPA qrCodeRepositoryJPA;

	@Override
    public QRCode save(QRCode qrCode) {
        return QRCodeEntityMapper.toDomain(qrCodeRepositoryJPA.save(QRCodeEntityMapper.toEntity(qrCode)));
    }

    @Override
    public boolean existsQRCode(String qrId) {
        return qrCodeRepositoryJPA.existsById(qrId);
    }

    @Override
    public Optional<QRCode> findById(String qrId) {
        return qrCodeRepositoryJPA.findById(qrId).map(QRCodeEntityMapper::toDomain);
    }

    @Override
    public QRCode update(QRCode qrCode) {
        return QRCodeEntityMapper.toDomain(qrCodeRepositoryJPA.save(QRCodeEntityMapper.toEntity(qrCode)));
    }

	

}
