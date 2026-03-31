package com.mitocode.qrpayment.infraestructure.in.messaging.mapper;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.command.CreateMerchantCommand;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.infraestructure.in.messaging.dto.MerchantKafkaRequest;

@Component
public class MerchantKafkaRequestMapper {

    public CreateMerchantCommand toCommand(MerchantKafkaRequest request) {
        if (request == null) {
            return null;
        }
        
        MerchantType merchantType = parseMerchantType(request.getMerchantType());
        
        return new CreateMerchantCommand(
        		request.getMerchantEmail(),
        		request.getMerchantName(),
        		merchantType,
                request.getCallbackUrl()
        );
    }
    
    private MerchantType parseMerchantType(String typeString) {
        return MerchantType.valueOf(typeString);
    }
}