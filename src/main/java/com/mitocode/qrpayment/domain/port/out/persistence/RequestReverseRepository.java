package com.mitocode.qrpayment.domain.port.out.persistence;

import java.util.Optional;

import com.mitocode.qrpayment.domain.model.entity.RequestReverse;

public interface RequestReverseRepository {
	
	void create(RequestReverse requestReverse);

    Optional<RequestReverse>  findByMerchantIdAndQrId(String merchantId, String qrId);

}
