package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class QRTypeTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada tipo de QR")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Dynamic", QRType.DYNAMIC.getType());
        assertEquals("Static", QRType.STATIC.getType());
    }
    
    @ParameterizedTest
    @EnumSource(QRType.class)
    @DisplayName("getDescription: no debería retornar null para ningún tipo")
    void getDescription_shouldNotReturnNull(QRType type) {
        assertNotNull(type.getType());
        assertFalse(type.getType().isEmpty());
    }
    
    @Test
    @DisplayName("valueOf: debería convertir correctamente de String a enum")
    void valueOf_shouldConvertFromString() {
        assertEquals(QRType.DYNAMIC, QRType.valueOf("DYNAMIC"));
        assertEquals(QRType.STATIC, QRType.valueOf("STATIC"));
    }
    
    @Test
    @DisplayName("valueOf: debería lanzar IllegalArgumentException para valores inválidos")
    void valueOf_shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> QRType.valueOf("INVALID"));
    }
}