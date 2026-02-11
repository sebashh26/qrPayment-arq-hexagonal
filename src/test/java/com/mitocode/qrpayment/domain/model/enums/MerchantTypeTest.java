package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class MerchantTypeTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada tipo de comercio")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Physical", MerchantType.PHYSICAL.getDescription());
        assertEquals("Digital", MerchantType.DIGITAL.getDescription());
    }
    
    @Test
    @DisplayName("isDigital: debería retornar true solo para MerchantType.DIGITAL")
    void isDigital_shouldReturnTrueOnlyForDigital() {
        assertTrue(MerchantType.DIGITAL.isDigital());
        assertFalse(MerchantType.PHYSICAL.isDigital());
    }
    
    @ParameterizedTest
    @EnumSource(value = MerchantType.class, names = {"DIGITAL"})
    @DisplayName("isDigital: debería retornar true para tipos digitales")
    void isDigital_shouldReturnTrueForDigitalTypes(MerchantType type) {
        assertTrue(type.isDigital());
    }
    
    @ParameterizedTest
    @EnumSource(value = MerchantType.class, names = {"PHYSICAL"})
    @DisplayName("isDigital: debería retornar false para tipos físicos")
    void isDigital_shouldReturnFalseForPhysicalTypes(MerchantType type) {
        assertFalse(type.isDigital());
    }
    
    @ParameterizedTest
    @EnumSource(MerchantType.class)
    @DisplayName("getDescription: no debería retornar null para ningún tipo")
    void getDescription_shouldNotReturnNull(MerchantType type) {
        assertNotNull(type.getDescription());
        assertFalse(type.getDescription().isEmpty());
    }
}