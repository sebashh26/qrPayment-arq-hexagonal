package com.mitocode.qrpayment.infraestructure.out.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mitocode.qrpayment.infraestructure.out.persistence.entity.RequestReverseEntity;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {

    @Bean
    DynamoDbClient dynamoDbClient() {
	    return DynamoDbClient.builder()
	            .region(Region.US_EAST_1) // tu región
	            .credentialsProvider(DefaultCredentialsProvider.builder().build())
	            .build();
	}


    @Bean
    DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        // DynamoDbClient es el cliente base de AWS SDK v2
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    DynamoDbTable<RequestReverseEntity> requestReverseTableBean(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(
                "apqr-request-reverse",
                TableSchema.fromBean(RequestReverseEntity.class)
        );
    }

}
