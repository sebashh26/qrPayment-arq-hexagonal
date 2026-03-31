package com.mitocode.qrpayment.infraestructure.in.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.command.CreateMerchantCommand;
import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.usecase.merchant.CreateMerchantUseCase;
import com.mitocode.qrpayment.infraestructure.in.messaging.dto.MerchantKafkaRequest;
import com.mitocode.qrpayment.infraestructure.in.messaging.dto.MerchantKafkaResponse;
import com.mitocode.qrpayment.infraestructure.in.messaging.mapper.MerchantKafkaRequestMapper;
import com.mitocode.qrpayment.infraestructure.in.messaging.mapper.MerchantKafkaResponseMapper;
import com.mitocode.qrpayment.infraestructure.in.messaging.producer.MerchantKafkaProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j	
public class MerchantKafkaConsumer {
	
	private final CreateMerchantUseCase createMerchantUseCase;
    private final MerchantKafkaProducer merchantKafkaProducer;
    private final MerchantKafkaRequestMapper merchantKafkaRequestMapper;
    private final MerchantKafkaResponseMapper merchantKafkaResponseMapper;

    @KafkaListener(topics = "${kafka.topics.request-merchant}", 
                   groupId = "${kafka.consumer.group-id}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void processMerchantCreationRequest(MerchantKafkaRequest request) {
        log.info("Received merchant creation request: {}", request.getRequestId());
        
        MerchantKafkaResponse response;
        
        try {
            // Mapear mensaje Kafka a comando del dominio (delegado al mapper)
            CreateMerchantCommand command = merchantKafkaRequestMapper.toCommand(request);
            
            // Delegar toda la lógica al caso de uso (siguiendo arquitectura hexagonal)
            MerchantDto merchantDto = createMerchantUseCase.execute(command);
            
            // Mapear respuesta del dominio a mensaje Kafka (delegado al mapper)
            response = merchantKafkaResponseMapper.toSuccessResponse(request, merchantDto);
                    
            log.info("Merchant created successfully: {}", merchantDto.getMerchantId());
            
        } catch (Exception e) {
            log.error("Error processing merchant creation request: {}", e.getMessage(), e);
            
            // Mapear error a mensaje Kafka (delegado al mapper)
            response = merchantKafkaResponseMapper.toErrorResponse(request, e);
        }
        
        // Enviar respuesta
        merchantKafkaProducer.sendResponse(response);
    }

}
