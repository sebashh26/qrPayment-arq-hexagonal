package com.mitocode.qrpayment.infraestructure.out.persistence.adapter.dynamo;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.domain.model.entity.RequestReverse;
import com.mitocode.qrpayment.domain.port.out.persistence.RequestReverseRepository;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.RequestReverseEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.RequestReverseEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.dynamo.impl.RequestReverseDynamoImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RqReversRepoDynamoAdapter implements RequestReverseRepository{
	
	private final RequestReverseDynamoImpl requestReverseDynamo;
	private static final Logger logger = LoggerFactory.getLogger(RqReversRepoDynamoAdapter.class);

	@Override
	public void create(RequestReverse requestReverse) {
		 RequestReverseEntity requestReverseEntity = RequestReverseEntityMapper.toEntity(requestReverse);

	        requestReverseDynamo.insertRequestReverse(requestReverseEntity);
		
	}

	@Override
	public Optional<RequestReverse> findByMerchantIdAndQrId(String merchantId, String qrId) {
		Optional<RequestReverseEntity> requestReverseEntityOpt =  requestReverseDynamo.findByMerchantIdAndQrId(merchantId,qrId);

        if (requestReverseEntityOpt.isPresent()){
            RequestReverseEntity requestReverseEntity = requestReverseEntityOpt.get();
            logger.info("RequestReverse found for merchantId: {} and qrId: {}", merchantId, qrId);
            logger.debug("RequestReverseEntity details: {}", requestReverseEntity.toString());
            return Optional.of(new RequestReverse(requestReverseEntity.getMerchantId(),requestReverseEntity.getQrId()));
        }
        return Optional.empty();
	}

}
