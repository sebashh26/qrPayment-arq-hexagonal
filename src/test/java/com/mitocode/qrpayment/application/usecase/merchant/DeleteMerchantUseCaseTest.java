package com.mitocode.qrpayment.application.usecase.merchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteMerchantUseCaseTest {

    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private MerchantToMerchantDto mapper;
    @InjectMocks
    private DeleteMerchantUseCase useCase;

    @Test
    void shouldThrowWhenMerchantNotFound() {
        when(merchantRepository.findById("id")).thenReturn(Optional.empty());
        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute("id"));
        assertEquals("Merchant not found", ex.getMessage());
    }

    @Test
    void shouldDeactivateAndSaveMerchant() {
        Merchant merchant = new Merchant("Name", "email@example.com", MerchantType.DIGITAL, "url");
        when(merchantRepository.findById("id")).thenReturn(Optional.of(merchant));
        when(merchantRepository.save(any(Merchant.class))).thenAnswer(inv -> inv.getArgument(0));
        MerchantDto expected = new MerchantDto();
        expected.setStatus(MerchantStatus.INACTIVE);
        when(mapper.buildMerchantDto(any(Merchant.class))).thenReturn(expected);

        MerchantDto result = useCase.execute("id");

        verify(merchantRepository).save(any(Merchant.class));
        assertEquals(MerchantStatus.INACTIVE, result.getStatus());
        assertFalse(merchant.isActive());
    }
}