package com.mitocode.qrpayment.infraestructure.in.messaging.producer;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.infraestructure.in.messaging.dto.MerchantKafkaResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MerchantKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topics.response-merchant}")
    private String responseTopic;

    public void sendResponse(MerchantKafkaResponse response) {
        try {
            log.info("Sending merchant response to topic {}: {}", responseTopic, response.getRequestId());
            
            String key = response.getCorrelationId() != null ? response.getCorrelationId() : response.getRequestId();
            
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(responseTopic, key, response);
            
            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    log.info("Merchant response sent successfully to topic {} with key {}: offset={}, partition={}", 
                        responseTopic, 
                        key,
                        result.getRecordMetadata().offset(),
                        result.getRecordMetadata().partition());
                } else {
                    log.error("Failed to send merchant response to topic {} with key {}: {}", 
                        responseTopic, 
                        key, 
                        exception.getMessage(), 
                        exception);
                }
            });
            
        } catch (Exception e) {
            log.error("Error sending merchant response to Kafka: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send response to Kafka", e);
        }
    }
}
