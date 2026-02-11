package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class QRStatusTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada estado de QR")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Active", QRStatus.ACTIVE.getStatus());
        assertEquals("Expired", QRStatus.EXPIRED.getStatus());
        assertEquals("Used", QRStatus.USED.getStatus());
        assertEquals("Inactive", QRStatus.INACTIVE.getStatus());
    }
    
    @ParameterizedTest
    @EnumSource(QRStatus.class)
    @DisplayName("getDescription: no debería retornar null para ningún estado")
    void getDescription_shouldNotReturnNull(QRStatus status) {
        assertNotNull(status.getStatus());
        assertFalse(status.getStatus().isEmpty());
    }
    
    @Test
    @DisplayName("valueOf: debería convertir correctamente de String a enum")
    void valueOf_shouldConvertFromString() {
        assertEquals(QRStatus.ACTIVE, QRStatus.valueOf("ACTIVE"));
        assertEquals(QRStatus.EXPIRED, QRStatus.valueOf("EXPIRED"));
        assertEquals(QRStatus.USED, QRStatus.valueOf("USED"));
        assertEquals(QRStatus.INACTIVE, QRStatus.valueOf("INACTIVE"));
    }
    
    @Test
    @DisplayName("valueOf: debería lanzar IllegalArgumentException para valores inválidos")
    void valueOf_shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> QRStatus.valueOf("INVALID"));
    }
}