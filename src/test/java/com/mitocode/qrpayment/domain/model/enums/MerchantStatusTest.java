package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class MerchantStatusTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada estado de merchant")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Active", MerchantStatus.ACTIVE.getSatus());
        assertEquals("Disabled", MerchantStatus.DISABLED.getSatus());
        assertEquals("Inactive", MerchantStatus.INACTIVE.getSatus());
    }
    
    @ParameterizedTest
    @EnumSource(MerchantStatus.class)
    @DisplayName("getDescription: no debería retornar null para ningún estado")
    void getDescription_shouldNotReturnNull(MerchantStatus status) {
        assertNotNull(status.getSatus());
        assertFalse(status.getSatus().isEmpty());
    }
    
    @Test
    @DisplayName("valueOf: debería convertir correctamente de String a enum")
    void valueOf_shouldConvertFromString() {
        assertEquals(MerchantStatus.ACTIVE, MerchantStatus.valueOf("ACTIVE"));
        assertEquals(MerchantStatus.DISABLED, MerchantStatus.valueOf("DISABLED"));
        assertEquals(MerchantStatus.INACTIVE, MerchantStatus.valueOf("INACTIVE"));
    }
    
    @Test
    @DisplayName("valueOf: debería lanzar IllegalArgumentException para valores inválidos")
    void valueOf_shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> MerchantStatus.valueOf("INVALID"));
    }
}