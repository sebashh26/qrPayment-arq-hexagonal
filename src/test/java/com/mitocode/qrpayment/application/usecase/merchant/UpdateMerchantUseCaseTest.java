package com.mitocode.qrpayment.application.usecase.merchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.application.command.UpdateMerchantCommand;
import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateMerchantUseCaseTest {

    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private MerchantToMerchantDto mapper;
    @InjectMocks
    private UpdateMerchantUseCase useCase;

    @Test
    void shouldThrowWhenMerchantNotFound() {
        UpdateMerchantCommand cmd = new UpdateMerchantCommand("id", "New", "new@example.com", MerchantType.DIGITAL);
        when(merchantRepository.findById("id")).thenReturn(Optional.empty());
        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(cmd));
        assertEquals("Merchant not found", ex.getMessage());
    }
    @Test
    void shouldThrowWhenDigitalCallBackMissing() {
    	Merchant merchant = new Merchant("Name", "email@example.com", MerchantType.DIGITAL, "cb");
    	when(merchantRepository.findById("id")).thenReturn(Optional.of(merchant));
    	UpdateMerchantCommand cmd = new UpdateMerchantCommand("id", null, "new@example.com", MerchantType.DIGITAL);
    	BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(cmd));
    	assertEquals("callbackUrl is required", ex.getMessage());
    }
    @Test
    void shouldUpdateWhenPhysicalCallBackMissing() {
    	Merchant merchant = new Merchant("Name", "email@example.com", MerchantType.DIGITAL, "cb");
        when(merchantRepository.findById("id")).thenReturn(Optional.of(merchant));
        when(merchantRepository.update(any(Merchant.class))).thenAnswer(invocation -> invocation.getArgument(0));
        MerchantDto expected = new MerchantDto();
        expected.setName("Updated");
        expected.setCallbackUrl(null);
        expected.setType(MerchantType.PHYSICAL);
        expected.setStatus(MerchantStatus.ACTIVE);
        expected.setEmail("email@example.com");
        expected.setMerchantId(merchant.getMerchantId());
        when(mapper.buildMerchantDto(any(Merchant.class))).thenReturn(expected);

        UpdateMerchantCommand cmd = new UpdateMerchantCommand("id", null, "Updated", MerchantType.PHYSICAL);
        MerchantDto result = useCase.execute(cmd);
        verify(merchantRepository).update(any(Merchant.class));
        assertEquals("Updated", result.getName());
        assertNull(result.getCallbackUrl());
        assertEquals(MerchantType.PHYSICAL, result.getType());
    }

    @Test
    void shouldUpdateMerchantSuccessfully() {
        Merchant merchant = new Merchant("Name", "email@example.com", MerchantType.DIGITAL, "cb");
        when(merchantRepository.findById("id")).thenReturn(Optional.of(merchant));
        when(merchantRepository.update(any(Merchant.class))).thenAnswer(invocation -> invocation.getArgument(0));
        MerchantDto expected = new MerchantDto();
        expected.setName("Updated");
        expected.setCallbackUrl("new");
        expected.setType(MerchantType.DIGITAL);
        expected.setStatus(MerchantStatus.ACTIVE);
        when(mapper.buildMerchantDto(any(Merchant.class))).thenReturn(expected);

        UpdateMerchantCommand cmd = new UpdateMerchantCommand("id", "Updated", "new@example.com", MerchantType.DIGITAL);
        MerchantDto result = useCase.execute(cmd);
        verify(merchantRepository).update(any(Merchant.class));
        assertEquals("Updated", result.getName());
        assertEquals("new", result.getCallbackUrl());
        assertEquals(MerchantType.DIGITAL, result.getType());
    }
}
