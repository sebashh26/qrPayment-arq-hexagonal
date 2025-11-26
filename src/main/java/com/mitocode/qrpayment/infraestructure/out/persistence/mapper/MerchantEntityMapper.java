package com.mitocode.qrpayment.infraestructure.out.persistence.mapper;

import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.MerchantEntity;

public class MerchantEntityMapper {
	
	private MerchantEntityMapper() {
		
	}
	
	public static MerchantEntity toEntity (Merchant merchant) {
		return new MerchantEntity(
				merchant.getMerchantId(),
				merchant.getEmail(),
				merchant.getName(),
				merchant.getType(),
				merchant.getCallBackUrl(),
				merchant.getStatus()
				);
	}
	
	public static Merchant toDomain (MerchantEntity merchantEntity) {
		Merchant merchant = new Merchant(
				merchantEntity.getName(),
				merchantEntity.getEmail(),
				merchantEntity.getType(),
				merchantEntity.getCallBackUrl()
        );
        merchant.setMerchantId(merchantEntity.getMerchantId());// esto hacemos para q no se pierda ya q en el new se genera un id con un uuid nuevo y no queemos que se pierda el que llego
        merchant.setStatus(merchantEntity.getStatus());//se hace lo mismo para el status
        return merchant;
	}
	

}
