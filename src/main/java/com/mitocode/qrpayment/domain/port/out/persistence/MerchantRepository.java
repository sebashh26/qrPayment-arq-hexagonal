package com.mitocode.qrpayment.domain.port.out.persistence;

import java.util.List;
import java.util.Optional;

import com.mitocode.qrpayment.domain.model.entity.Merchant;

public interface MerchantRepository {
	
	Merchant save(Merchant merchant);
	Merchant update(Merchant merchant);
	boolean existsByEmail(String email);
	boolean existsById(String id);
	Optional<Merchant> findById(String id);
	List<Merchant> findAll();
	//Optional<Merchant> findByEmail(String email);
	//Merchant deletebyId(String id);
}
