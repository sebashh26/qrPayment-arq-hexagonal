package com.mitocode.qrpayment.infraestructure.in.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantKafkaRequest {
	@JsonProperty("request_id")
    private String requestId;
    
    @JsonProperty("merchant_name")
    @NotBlank(message = "Merchant name is required")
    private String merchantName;
    
    @JsonProperty("merchant_email")
    @NotBlank(message = "Merchant email is required")
    @Email(message = "Invalid email format")
    private String merchantEmail;
    
    @JsonProperty("merchant_type")
    private String merchantType;

    @JsonProperty("callback_url")
    private String callbackUrl;
    
    @JsonProperty("correlation_id")
    private String correlationId;
    
    @JsonProperty("timestamp")
    private String timestamp;

}
