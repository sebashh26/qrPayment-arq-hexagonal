package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mitocode.qrpayment.domain.model.entity.Wallet;
import com.mitocode.qrpayment.domain.port.out.persistence.WalletRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.WalletEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.WalletEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa.WalletRepositoryJPA;

@Repository
public class WalletRepositoryJPAAdapter implements WalletRepository {
	
	@Autowired
	private WalletRepositoryJPA jpa;

	@Override
    public Wallet save(Wallet wallet) {
        WalletEntity walletEntity = jpa.save(WalletEntityMapper.toEntity(wallet));
        return WalletEntityMapper.toDomain(walletEntity);
    }

    @Override
    public Optional<Wallet> findById(String walletId) {
        Optional<WalletEntity>  walletEntity = jpa.findById(walletId);

        return WalletEntityMapper.toDomainOpt(walletEntity);
    }

}
