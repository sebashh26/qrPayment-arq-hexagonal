package com.mitocode.qrpayment.application.usecase.merchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

@ExtendWith(MockitoExtension.class)
public class GetAllMerchantUseCaseTest {

	@Mock
	private MerchantRepository merchantRepository;
	@Mock
	private MerchantToMerchantDto mapper;
	@InjectMocks
	private GetAllMerchantUseCase useCase;

	@Test
	void shouldReturnAllMappedMerchants() {
		Merchant merchant1 = new Merchant("Name1", "email1@example.com", MerchantType.DIGITAL, "url1");
		Merchant merchant2 = new Merchant("Name2", "email2@example.com", MerchantType.DIGITAL, "url2");
		when(merchantRepository.findAll()).thenReturn(Arrays.asList(merchant1, merchant2));
		MerchantDto dto1 = new MerchantDto();
		dto1.setName("Name1");
		MerchantDto dto2 = new MerchantDto();
		dto2.setName("Name2");
		when(mapper.buildMerchantDto(merchant1)).thenReturn(dto1);
		when(mapper.buildMerchantDto(merchant2)).thenReturn(dto2);

		List<MerchantDto> results = useCase.execute();
		assertEquals(2, results.size());
		assertEquals("Name1", results.get(0).getName());
		assertEquals("Name2", results.get(1).getName());
	}

}
