package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.MerchantEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.MerchantEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.MerchantRepositoryJDBC;

//sirve como conector entre los servicios de coneccion MerchantRepositoryJDBC de la bd y el domino de forma desacoplada
public class MerchantRepositoryJDBCAdapter implements MerchantRepository {
	//este es el puerto de salida que conecta con la bd
	private final MerchantRepositoryJDBC merchantRepositoryJDBC;
	
	public MerchantRepositoryJDBCAdapter(MerchantRepositoryJDBC merchantRepositoryJDBC) {
        this.merchantRepositoryJDBC = merchantRepositoryJDBC;
    }

	@Override
	public Merchant save(Merchant merchant) {
		// TODO Auto-generated method stub
		MerchantEntity merchantEntity= this.merchantRepositoryJDBC.save(MerchantEntityMapper.toEntity(merchant));
		return MerchantEntityMapper.toDomain(merchantEntity);
	}

	@Override
	public Merchant update(Merchant merchant) {
		MerchantEntity entity = this.merchantRepositoryJDBC.update(MerchantEntityMapper.toEntity(merchant));
        return MerchantEntityMapper.toDomain(entity);
	}

	@Override
	public boolean existsByEmail(String email) {
		return this.merchantRepositoryJDBC.existsByEmail(email);
	}

	@Override
	public boolean existsById(String id) {
		return this.merchantRepositoryJDBC.existsById(id);
	}

	@Override
	public Optional<Merchant> findById(String id) {
		return this.merchantRepositoryJDBC.findById(id)
                .map(MerchantEntityMapper::toDomain);
	}

	@Override
	public List<Merchant> findAll() {
		return this.merchantRepositoryJDBC.findAll()
                .stream()
                .map(MerchantEntityMapper::toDomain)
                .collect(Collectors.toList());
	}

//	@Override
//	public Optional<Merchant> findByEmail(String email) {
//		// TODO Auto-generated method stub
//		return Optional.empty();
//	}

//	@Override
//	public Merchant deletebyId(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
