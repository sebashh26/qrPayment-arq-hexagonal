package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitocode.qrpayment.infraestructure.out.persistence.entity.QRCodeEntity;

@Repository
public interface QRCodeRepositoryJPA extends JpaRepository<QRCodeEntity, String> {

    boolean existsById(String id);
}
