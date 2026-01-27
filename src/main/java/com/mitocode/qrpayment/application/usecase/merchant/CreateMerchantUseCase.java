package com.mitocode.qrpayment.application.usecase.merchant;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.command.CreateMerchantCommand;
import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.exception.InvalidRequestException;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CreateMerchantUseCase {
	
	private final MerchantRepository merchantRepository;
	private final MerchantToMerchantDto mapper;
	
	
	public MerchantDto execute(CreateMerchantCommand merchantCmd) {

        if (merchantRepository.existsByEmail(merchantCmd.getEmail())){
            throw new InvalidRequestException("Email already exists");
        }

        if ( merchantCmd.getType().isDigital() 
                && (merchantCmd.getCallbackUrl() == null || merchantCmd.getCallbackUrl().isEmpty()) ) {
                throw new InvalidRequestException("callbackUrl is required");
        }

        Merchant merchant  = new Merchant(
                merchantCmd.getName(),
                merchantCmd.getEmail(),
                merchantCmd.getType(),
                merchantCmd.getCallbackUrl()
        );

        Merchant merchantResult = merchantRepository.save(merchant);

        return mapper.buildMerchantDto(merchantResult);

    }

}
