package com.mitocode.qrpayment.application.usecase.merchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.mitocode.qrpayment.application.command.CreateMerchantCommand;
import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.exception.InvalidRequestException;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

@ExtendWith(MockitoExtension.class)
public class CreateMerchantUseCaseTest {

	// Usa @mock para dependencias externas que quieres simular.
	// Usa @InjectMock para la clase que realmente quieres probar, con sus
	// dependencias inyectadas automáticamente.
	@Mock
	private MerchantRepository merchantRepository;

	@Mock
	private MerchantToMerchantDto mapper;

	@InjectMocks
	private CreateMerchantUseCase useCase;

	private CreateMerchantCommand command;

	@BeforeEach
	void setUp() {
		command = new CreateMerchantCommand("http://cb", "email@example.com", "Merchant Test", MerchantType.DIGITAL);
	}

	@Test
	void shouldThrowWhenEmailAlreadyExists() {
		when(merchantRepository.existsByEmail(command.getEmail())).thenReturn(true);
		InvalidRequestException ex = assertThrows(InvalidRequestException.class, () -> useCase.execute(command));
		assertEquals("Email already exists", ex.getMessage());
	}
	
	@Test
    void shouldThrowWhenDigitalMerchantWithoutCallback() {
        command = new CreateMerchantCommand(null, "email@example.com", "Merchant Test", MerchantType.DIGITAL );
        when(merchantRepository.existsByEmail(command.getEmail())).thenReturn(false);
        InvalidRequestException ex = assertThrows(InvalidRequestException.class, () -> useCase.execute(command));
        assertEquals("callbackUrl is required", ex.getMessage());
    }

	//aqui solo se valida el negocio no la logica interna del dominio
    @Test
    void shouldCreateMerchantSuccessfully() {
        // merchant does not exist
        when(merchantRepository.existsByEmail(command.getEmail())).thenReturn(false);
        // capture save argument and return it
        when(merchantRepository.save(any(Merchant.class))).thenAnswer((Answer<Merchant>) invocation -> invocation.getArgument(0));

        MerchantDto expectedDto = new MerchantDto();
        expectedDto.setMerchantId(UUID.randomUUID().toString());
        expectedDto.setName(command.getName());
        expectedDto.setEmail(command.getEmail());
        expectedDto.setType(command.getType());
        expectedDto.setCallbackUrl(command.getCallbackUrl());
        expectedDto.setStatus(MerchantStatus.ACTIVE);
        when(mapper.buildMerchantDto(any(Merchant.class))).thenReturn(expectedDto);

        MerchantDto result = useCase.execute(command);

        //verify q se haya invocado de alguina manera  los metodos
        verify(merchantRepository).save(any(Merchant.class));
        verify(mapper).buildMerchantDto(any(Merchant.class));

        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getEmail(), result.getEmail());
        assertEquals(expectedDto.getCallbackUrl(), result.getCallbackUrl());
        assertEquals(expectedDto.getType(), result.getType());
        assertNotNull(result.getMerchantId());
    }
}
