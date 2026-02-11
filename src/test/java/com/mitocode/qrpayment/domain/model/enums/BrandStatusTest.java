package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class BrandStatusTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada estado de marca")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Authorized", BrandStatus.AUTHORIZED.getDescription());
        assertEquals("Pending", BrandStatus.PENDING.getDescription());
        assertEquals("Denied", BrandStatus.DENIED.getDescription());
    }
    
    @ParameterizedTest
    @EnumSource(BrandStatus.class)
    @DisplayName("getDescription: no debería retornar null para ningún estado")
    void getDescription_shouldNotReturnNull(BrandStatus status) {
        assertNotNull(status.getDescription());
        assertFalse(status.getDescription().isEmpty());
    }
    
    @Test
    @DisplayName("valueOf: debería convertir correctamente de String a enum")
    void valueOf_shouldConvertFromString() {
        assertEquals(BrandStatus.AUTHORIZED, BrandStatus.valueOf("AUTHORIZED"));
        assertEquals(BrandStatus.PENDING, BrandStatus.valueOf("PENDING"));
        assertEquals(BrandStatus.DENIED, BrandStatus.valueOf("DENIED"));
    }
    
    @Test
    @DisplayName("valueOf: debería lanzar IllegalArgumentException para valores inválidos")
    void valueOf_shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> BrandStatus.valueOf("INVALID"));
    }
}