package com.mitocode.qrpayment.infraestructure.in.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantKafkaResponse {
	
	@JsonProperty("request_id")
    private String requestId;
    
    @JsonProperty("correlation_id")
    private String correlationId;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("merchant_id")
    private String merchantId;
    
    @JsonProperty("merchant_name")
    private String merchantName;
    
    @JsonProperty("merchant_email")
    private String merchantEmail;
    
    @JsonProperty("merchant_type")
    private String merchantType;
    
    @JsonProperty("merchant_status")
    private String merchantStatus;
    
    @JsonProperty("error_code")
    private String errorCode;
    
    @JsonProperty("error_message")
    private String errorMessage;
    
    @JsonProperty("timestamp")
    private String timestamp;

}
