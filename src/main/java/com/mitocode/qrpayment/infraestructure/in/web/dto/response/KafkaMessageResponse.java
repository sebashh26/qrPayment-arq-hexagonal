package com.mitocode.qrpayment.infraestructure.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta del envío de mensaje a Kafka")
public class KafkaMessageResponse {

    @JsonProperty("success")
    @Schema(description = "Indica si el mensaje fue enviado exitosamente", example = "true")
    private boolean success;

    @JsonProperty("message")
    @Schema(description = "Mensaje descriptivo del resultado", example = "Message sent successfully")
    private String message;

    @JsonProperty("topic")
    @Schema(description = "Topic al que se envió el mensaje", example = "request-merchant")
    private String topic;

    @JsonProperty("partition")
    @Schema(description = "Partición donde se almacenó el mensaje", example = "0")
    private Integer partition;

    @JsonProperty("offset")
    @Schema(description = "Offset del mensaje en la partición", example = "123")
    private Long offset;

    @JsonProperty("key")
    @Schema(description = "Clave del mensaje enviado", example = "merchant-123")
    private String key;

    @JsonProperty("timestamp")
    @Schema(description = "Timestamp del envío", example = "2025-01-15T10:30:00")
    private String timestamp;

    @JsonProperty("error_message")
    @Schema(description = "Mensaje de error si el envío falló")
    private String errorMessage;
}