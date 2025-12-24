package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitocode.qrpayment.infraestructure.out.persistence.entity.WalletEntity;

@Repository
public interface WalletRepositoryJPA extends JpaRepository<WalletEntity, String> {
}