package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyCodeTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada código de moneda")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Soles", CurrencyCode.PEN.getCurrencyCode());
        assertEquals("Dollars", CurrencyCode.USD.getCurrencyCode());
    }
    
    @ParameterizedTest
    @EnumSource(CurrencyCode.class)
    @DisplayName("getDescription: no debería retornar null para ningún código de moneda")
    void getDescription_shouldNotReturnNull(CurrencyCode code) {
        assertNotNull(code.getCurrencyCode());
        assertFalse(code.getCurrencyCode().isEmpty());
    }
    
    @Test
    @DisplayName("valueOf: debería convertir correctamente de String a enum")
    void valueOf_shouldConvertFromString() {
        assertEquals(CurrencyCode.PEN, CurrencyCode.valueOf("PEN"));
        assertEquals(CurrencyCode.USD, CurrencyCode.valueOf("USD"));
    }
    
    @Test
    @DisplayName("valueOf: debería lanzar IllegalArgumentException para valores inválidos")
    void valueOf_shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> CurrencyCode.valueOf("EUR"));
    }
}