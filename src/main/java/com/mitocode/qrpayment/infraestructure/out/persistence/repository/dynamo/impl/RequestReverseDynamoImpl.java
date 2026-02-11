package com.mitocode.qrpayment.infraestructure.out.persistence.repository.dynamo.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.mitocode.qrpayment.infraestructure.out.persistence.entity.RequestReverseEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.dynamo.RequestReverseDynamo;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@RequiredArgsConstructor
@Repository
public class RequestReverseDynamoImpl implements RequestReverseDynamo {

	private final DynamoDbTable<RequestReverseEntity> requestReverseTable;

	@Override
	public void insertRequestReverse(RequestReverseEntity requestReverse) {
		requestReverseTable.putItem(requestReverse);

	}

	@Override
	public Optional<RequestReverseEntity> findByMerchantIdAndQrId(String merchantId, String qrId) {
		QueryConditional queryConditional = QueryConditional
				.keyEqualTo(Key.builder().partitionValue(merchantId).build());

		Expression filterExpression = Expression.builder().expression("#qrId = :qrId")
				.expressionNames(Collections.singletonMap("#qrId", "qrId"))
				.expressionValues(Collections.singletonMap(":qrId", AttributeValue.builder().s(qrId).build())).build();

		QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder().queryConditional(queryConditional)
				.filterExpression(filterExpression).build();

		return requestReverseTable.query(queryEnhancedRequest).items().stream().findFirst();
//        List<RequestReverseEntity>  requestReverseEntities = requestReverseTable.query(queryEnhancedRequest)
//                .items()
//                .stream()
//                .collect(Collectors.toList());
//
//        if (requestReverseEntities.isEmpty()){
//            return Optional.empty();
//        }

		// return Optional.of(requestReverseEntities.get(0));
	}

}
