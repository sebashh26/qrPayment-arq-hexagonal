package com.mitocode.qrpayment.infraestructure.out.persistence.repository.dynamo;

import java.util.Optional;

import com.mitocode.qrpayment.infraestructure.out.persistence.entity.RequestReverseEntity;

public interface RequestReverseDynamo {

	void insertRequestReverse(RequestReverseEntity requestReverse);

    Optional<RequestReverseEntity> findByMerchantIdAndQrId(String merchantId, String qrId);
}
