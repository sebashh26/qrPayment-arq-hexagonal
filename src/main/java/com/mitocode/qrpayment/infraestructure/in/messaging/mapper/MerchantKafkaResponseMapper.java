package com.mitocode.qrpayment.infraestructure.in.messaging.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.infraestructure.in.messaging.dto.MerchantKafkaRequest;
import com.mitocode.qrpayment.infraestructure.in.messaging.dto.MerchantKafkaResponse;

@Component
public class MerchantKafkaResponseMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public MerchantKafkaResponse toSuccessResponse(MerchantKafkaRequest request, MerchantDto merchantDto) {
        return MerchantKafkaResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .correlationId(request.getCorrelationId())
                .status("SUCCESS")
                .merchantId(merchantDto.getMerchantId())
                .merchantName(merchantDto.getName())
                .merchantEmail(merchantDto.getEmail())
                .merchantType(merchantDto.getType() != null ? merchantDto.getType().name() : null)
                .merchantStatus(merchantDto.getStatus() != null ? merchantDto.getStatus().name() : null)
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
    }

    public MerchantKafkaResponse toErrorResponse(MerchantKafkaRequest request, Exception exception) {
        return MerchantKafkaResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .correlationId(request.getCorrelationId())
                .status("ERROR")
                .errorCode(mapErrorCode(exception))
                .errorMessage(exception.getMessage())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
    }

    private String mapErrorCode(Exception exception) {
        if (exception instanceof IllegalArgumentException) {
            return "01"; // Bad request
        }
        if (exception instanceof RuntimeException) {
            return "05"; // Internal error
        }
        return "05"; // Default internal error
    }
}
