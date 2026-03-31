package com.mitocode.qrpayment.infraestructure.in.web.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.kafka.KafkaMessageRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.KafkaMessageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${kafka.topics.request-merchant}")
    private String merchantRequestTopic;
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public KafkaMessageResponse sendToMerchantTopic(KafkaMessageRequest request) {
        String topic = request.getTopic() != null ? request.getTopic() : merchantRequestTopic;
        return sendMessage(topic, request.getKey(), request.getMessage());
    }

    public KafkaMessageResponse sendRawJson(String topic, String key, String jsonMessage) {
        return sendMessage(topic, key, jsonMessage);
    }

    private KafkaMessageResponse sendMessage(String topic, String key, String jsonMessage) {
        try {
            // Validar que el mensaje sea JSON válido y añadir request-id si no existe
            String processedMessage = ensureRequestId(jsonMessage);
            
            // Convertir el string JSON a Object para que KafkaTemplate lo serialize correctamente
            Object messageObject = objectMapper.readValue(processedMessage, Object.class);
            
            log.info("Sending message to topic '{}' with key '{}'", topic, key);
            
            // Enviar mensaje de forma asíncrona
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, messageObject);
            
            // Esperar el resultado (para fines de demostración, en producción considerar async)
            SendResult<String, Object> result = future.get();
            
            log.info("Message sent successfully to topic '{}', partition: {}, offset: {}", 
                topic, 
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
            
            return KafkaMessageResponse.builder()
                    .success(true)
                    .message("Message sent successfully")
                    .topic(topic)
                    .partition(result.getRecordMetadata().partition())
                    .offset(result.getRecordMetadata().offset())
                    .key(key)
                    .timestamp(LocalDateTime.now().format(formatter))
                    .build();
                    
        } catch (JsonProcessingException e) {
            log.error("Invalid JSON message: {}", e.getMessage());
            return buildErrorResponse(topic, key, "Invalid JSON format: " + e.getMessage());
            
        } catch (Exception e) {
            log.error("Error sending message to Kafka: {}", e.getMessage(), e);
            return buildErrorResponse(topic, key, "Failed to send message: " + e.getMessage());
        }
    }
    
    private String ensureRequestId(String jsonMessage) throws JsonProcessingException {
        if (jsonMessage == null || jsonMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        
        // Parsear JSON
        JsonNode jsonNode = objectMapper.readTree(jsonMessage);
        
        // Verificar si ya tiene request_id
        if (!jsonNode.has("request_id") || 
            jsonNode.get("request_id").isNull() || 
            jsonNode.get("request_id").asText().trim().isEmpty()) {
            
            // Generar un UUID único para trazabilidad
            String uniqueRequestId = "req-" + UUID.randomUUID().toString().substring(0, 8);
            
            // Añadir el request_id al JSON
            ObjectNode objectNode = (ObjectNode) jsonNode;
            objectNode.put("request_id", uniqueRequestId);
            
            log.info("Generated unique request_id: {} for message", uniqueRequestId);
            
            return objectMapper.writeValueAsString(objectNode);
        }
        
        return jsonMessage;
    }
    
    private void validateJsonMessage(String jsonMessage) throws JsonProcessingException {
        if (jsonMessage == null || jsonMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        
        // Validar que sea JSON válido
        JsonNode jsonNode = objectMapper.readTree(jsonMessage);
        
        // Validación opcional: verificar que tenga campos mínimos para merchant request
        if (!jsonNode.has("request_id")) {
            log.warn("JSON message doesn't have 'request_id' field, this might cause processing issues");
        }
    }
    
    private KafkaMessageResponse buildErrorResponse(String topic, String key, String errorMessage) {
        return KafkaMessageResponse.builder()
                .success(false)
                .message("Failed to send message")
                .topic(topic)
                .key(key)
                .errorMessage(errorMessage)
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
    }
}