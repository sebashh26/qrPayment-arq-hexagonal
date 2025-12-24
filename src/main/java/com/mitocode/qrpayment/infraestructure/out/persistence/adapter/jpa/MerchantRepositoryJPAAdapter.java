package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.MerchantEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.MerchantEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa.MerchantRepositoryJPA;

@Component//estereotipo
public class MerchantRepositoryJPAAdapter implements MerchantRepository{
	
	@Autowired
	private MerchantRepositoryJPA merchantRepositoryJPA;

	@Override
	public Merchant save(Merchant merchant) {
		MerchantEntity merchantEntity= this.merchantRepositoryJPA.save(MerchantEntityMapper.toEntity(merchant));
		return MerchantEntityMapper.toDomain(merchantEntity);
	}

	@Override
	public Merchant update(Merchant merchant) {
		MerchantEntity entity = this.merchantRepositoryJPA.save(MerchantEntityMapper.toEntity(merchant));
        return MerchantEntityMapper.toDomain(entity);
	}

	@Override
	public boolean existsByEmail(String email) {
		return this.merchantRepositoryJPA.existsByEmail(email);
	}

	@Override
	public boolean existsById(String id) {
		return this.merchantRepositoryJPA.existsById(id);
	}

	@Override
	public Optional<Merchant> findById(String id) {
		return this.merchantRepositoryJPA.findById(id)
                .map(MerchantEntityMapper::toDomain);
	}

	@Override
	public List<Merchant> findAll() {
		return this.merchantRepositoryJPA.findAll()
                .stream()
                .map(MerchantEntityMapper::toDomain)
                .collect(Collectors.toList());
	}

}
