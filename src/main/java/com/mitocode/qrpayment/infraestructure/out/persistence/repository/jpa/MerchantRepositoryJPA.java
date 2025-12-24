package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitocode.qrpayment.infraestructure.out.persistence.entity.MerchantEntity;

@Repository
public interface MerchantRepositoryJPA extends JpaRepository<MerchantEntity, String> {

    Optional<MerchantEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
