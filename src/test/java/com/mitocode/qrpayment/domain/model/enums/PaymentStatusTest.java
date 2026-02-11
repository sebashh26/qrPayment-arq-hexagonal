package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada estado de pago")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Pendiente", PaymentStatus.PENDING.getDescription());
        assertEquals("Authorized", PaymentStatus.AUTHORIZED.getDescription());
        assertEquals("Denied", PaymentStatus.DENIED.getDescription());
        assertEquals("Refunded", PaymentStatus.REFUNDED.getDescription());
    }
    
    @ParameterizedTest
    @EnumSource(PaymentStatus.class)
    @DisplayName("getDescription: no debería retornar null para ningún estado")
    void getDescription_shouldNotReturnNull(PaymentStatus status) {
        assertNotNull(status.getDescription());
        assertFalse(status.getDescription().isEmpty());
    }
    
    @Test
    @DisplayName("valueOf: debería convertir correctamente de String a enum")
    void valueOf_shouldConvertFromString() {
        assertEquals(PaymentStatus.PENDING, PaymentStatus.valueOf("PENDING"));
        assertEquals(PaymentStatus.AUTHORIZED, PaymentStatus.valueOf("AUTHORIZED"));
        assertEquals(PaymentStatus.DENIED, PaymentStatus.valueOf("DENIED"));
        assertEquals(PaymentStatus.REFUNDED, PaymentStatus.valueOf("REFUNDED"));
    }
    
    @Test
    @DisplayName("valueOf: debería lanzar IllegalArgumentException para valores inválidos")
    void valueOf_shouldThrowExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> PaymentStatus.valueOf("INVALID"));
    }
}