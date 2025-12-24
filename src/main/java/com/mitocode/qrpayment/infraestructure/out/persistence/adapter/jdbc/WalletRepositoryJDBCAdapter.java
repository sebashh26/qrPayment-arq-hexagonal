package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc;

import java.util.Optional;

import com.mitocode.qrpayment.domain.model.entity.Wallet;
import com.mitocode.qrpayment.domain.port.out.persistence.WalletRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.WalletEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.WalletEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.WalletRepositoryJDBC;

public class WalletRepositoryJDBCAdapter implements WalletRepository {
	
	private final WalletRepositoryJDBC jdbc;

    public WalletRepositoryJDBCAdapter(WalletRepositoryJDBC jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity walletEntity = jdbc.save(WalletEntityMapper.toEntity(wallet));
        return WalletEntityMapper.toDomain(walletEntity);
    }

    @Override
    public Optional<Wallet> findById(String walletId) {
        Optional<WalletEntity>  walletEntity = jdbc.findById(walletId);

        return WalletEntityMapper.toDomainOpt(walletEntity);
    }

}
