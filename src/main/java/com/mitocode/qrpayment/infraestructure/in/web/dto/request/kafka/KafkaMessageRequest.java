package com.mitocode.qrpayment.infraestructure.in.web.dto.request.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para enviar mensaje a Kafka")
public class KafkaMessageRequest {

    @JsonProperty("topic")
    @Schema(description = "Nombre del topic Kafka", example = "request-merchant", defaultValue = "request-merchant")
    private String topic = "request-merchant";

    @JsonProperty("key")
    @Schema(description = "Clave del mensaje (opcional)", example = "merchant-123")
    private String key;

    @JsonProperty("message")
    @NotBlank(message = "El mensaje JSON es requerido")
    @Schema(description = "Mensaje JSON a enviar", 
            example = "{\n  \"request_id\": \"req-123\",\n  \"merchant_name\": \"Mi Tienda\",\n  \"merchant_email\": \"tienda@email.com\",\n  \"merchant_type\": \"PHYSICAL\",\n  \"correlation_id\": \"corr-456\",\n  \"timestamp\": \"2025-01-15T10:30:00\"\n}")
    private String message;

    @JsonProperty("headers")
    @Schema(description = "Headers adicionales para el mensaje (opcional)")
    private String headers;
}