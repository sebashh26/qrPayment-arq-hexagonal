package com.mitocode.qrpayment.infraestructure.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.kafka.KafkaMessageRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.KafkaMessageResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.KafkaMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Kafka Messages", description = "API para enviar mensajes directamente a Kafka topics")
public class KafkaMessageController {

    private final KafkaMessageService kafkaMessageService;

    @PostMapping("/send-to-merchant-topic")
    @Operation(summary = "Enviar JSON a topic request-merchant", 
               description = "Permite enviar un mensaje JSON directamente al topic request-merchant para testing")
    public ResponseEntity<KafkaMessageResponse> sendToMerchantTopic(
            @Parameter(description = "JSON message to send to request-merchant topic")
            @Valid @RequestBody KafkaMessageRequest request) {
        
        log.info("Received request to send message to request-merchant topic");
        
        KafkaMessageResponse response = kafkaMessageService.sendToMerchantTopic(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-raw-json")
    @Operation(summary = "Enviar JSON crudo a cualquier topic", 
               description = "Permite enviar un JSON string crudo a cualquier topic específico")
    public ResponseEntity<KafkaMessageResponse> sendRawJson(
            @Parameter(description = "Topic name") @RequestParam String topic,
            @Parameter(description = "Message key (optional)") @RequestParam(required = false) String key,
            @Parameter(description = "Raw JSON string") @RequestBody String jsonMessage) {
        
        log.info("Received request to send raw JSON to topic: {}", topic);
        
        KafkaMessageResponse response = kafkaMessageService.sendRawJson(topic, key, jsonMessage);
        
        return ResponseEntity.ok(response);
    }
}
