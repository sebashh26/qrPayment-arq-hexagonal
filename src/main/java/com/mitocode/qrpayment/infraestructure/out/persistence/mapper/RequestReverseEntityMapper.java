package com.mitocode.qrpayment.infraestructure.out.persistence.mapper;

import com.mitocode.qrpayment.domain.model.entity.RequestReverse;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.RequestReverseEntity;

public class RequestReverseEntityMapper {

	private RequestReverseEntityMapper() {
	}

	public static RequestReverseEntity toEntity(RequestReverse dto) {
		RequestReverseEntity entity = new RequestReverseEntity();
		entity.setMerchantId(dto.getMerchantId());
		entity.setQrId(dto.getQrId());
		return entity;
	}

}
