package com.mitocode.qrpayment.infraestructure.out.persistence.mapper;

import java.util.Optional;

import com.mitocode.qrpayment.domain.model.entity.Wallet;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.WalletEntity;

public class WalletEntityMapper {

	private WalletEntityMapper() {
	}

	public static WalletEntity toEntity(Wallet wallet) {
		WalletEntity entity = new WalletEntity();
		entity.setWalletId(wallet.getWallerId());
		entity.setDescription(wallet.getDescription());
		entity.setStatus(wallet.getStatus());
		entity.setName(wallet.getName());
		return entity;
	}

	public static Wallet toDomain(WalletEntity entity) {
		return new Wallet(entity.getWalletId(), entity.getName(), entity.getDescription(), entity.getStatus());
	}

	public static Optional<Wallet> toDomainOpt(Optional<WalletEntity> entityOpt) {
		if (entityOpt.isPresent()) {
			WalletEntity entity = entityOpt.get();
			Wallet wallet = new Wallet(entity.getWalletId(), entity.getName(), entity.getDescription(),
					entity.getStatus());
			return Optional.of(wallet);
		}
		return Optional.empty();

	}

}
