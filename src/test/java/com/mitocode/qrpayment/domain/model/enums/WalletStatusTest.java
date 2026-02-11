package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class WalletStatusTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada estado de wallet")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Active", WalletStatus.ACTIVE.getDescription());
        assertEquals("Inactive", WalletStatus.INACTIVE.getDescription());
    }
    
    @ParameterizedTest
    @EnumSource(WalletStatus.class)
    @DisplayName("getDescription: no debería retornar null para ningún estado")
    void getDescription_shouldNotReturnNull(WalletStatus status) {
        assertNotNull(status.getDescription());
        assertFalse(status.getDescription().isEmpty());
    }
    
    @Test
    @DisplayName("valueOf: debería convertir correctamente de String a enum")
    void valueOf_shouldConvertFromString() {
        assertEquals(WalletStatus.ACTIVE, WalletStatus.valueOf("ACTIVE"));
        assertEquals(WalletStatus.INACTIVE, WalletStatus.valueOf("INACTIVE"));
    }
    
    @Test
    @DisplayName("valueOf: debería lanzar IllegalArgumentException para valores inválidos")
    void valueOf_shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> WalletStatus.valueOf("INVALID"));
    }
}