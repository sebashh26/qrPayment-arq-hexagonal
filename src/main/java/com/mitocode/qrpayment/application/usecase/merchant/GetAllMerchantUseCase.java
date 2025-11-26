package com.mitocode.qrpayment.application.usecase.merchant;

import java.util.List;
import java.util.stream.Collectors;

import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

public class GetAllMerchantUseCase {
	
	private final MerchantRepository merchantRepository;
    private final MerchantToMerchantDto mapper;


    public GetAllMerchantUseCase(MerchantRepository merchantRepository,
                                 MerchantToMerchantDto mapper
    ) {
        this.merchantRepository = merchantRepository;
        this.mapper = mapper;
    }

    public List<MerchantDto>  execute() {

        return merchantRepository.findAll()
                .stream()
                .map(mapper:: buildMerchantDto)
                .collect(Collectors.toList());

    }
	
//	-.toList() → devuelve una lista inmutable (no puedes hacer add, remove, etc.)
//	- .collect(Collectors.toList()) → devuelve una lista mutable
//	List<String> lista = stream.toList();
//	lista.add("nuevo"); // ❌ lanza UnsupportedOperationException

//	List<String> lista2 = stream.collect(Collectors.toList());
//	lista2.add("nuevo"); // ✅ funciona

//lo mismo q el metodo de arriba pero con lo q menciona de la mutabilidad	
//	public List<MerchantResponseDTO> execute() {
//		return merchantRepository.findAll().stream()
//				.map(mappper::buildMerchantResponse)
//				.collect(Collectors.toList());
//	}

}
