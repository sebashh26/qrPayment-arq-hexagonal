package com.mitocode.qrpayment.domain.port.out.persistence;

import java.util.Optional;

import com.mitocode.qrpayment.domain.model.entity.Wallet;

public interface WalletRepository {
	
	Wallet save(Wallet wallet);
	Optional<Wallet> findById(String id);
	

}
