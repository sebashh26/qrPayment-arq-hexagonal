package com.mitocode.qrpayment.domain.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BrandTypeTest {

    @Test
    @DisplayName("getDescription: debería retornar la descripción correcta para cada tipo de tarjeta")
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Visa", BrandType.VISA.getDescription());
        assertEquals("Amex", BrandType.AMEX.getDescription());
        assertEquals("Mastercard", BrandType.MASTERCARD.getDescription());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123"})
    @DisplayName("fromCardNumber: debería lanzar IllegalArgumentException para números de tarjeta inválidos")
    void fromCardNumber_shouldThrowExceptionForInvalidCardNumbers(String cardNumber) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> BrandType.fromCardNumber(cardNumber));
        
        assertEquals("Invalid card number", exception.getMessage());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"4111111111111111", "4000000000000000", "4999999999999999"})
    @DisplayName("fromCardNumber: debería identificar correctamente tarjetas VISA")
    void fromCardNumber_shouldIdentifyVisaCards(String cardNumber) {
        assertEquals(BrandType.VISA, BrandType.fromCardNumber(cardNumber));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "5111111111111111", "5211111111111111", "5311111111111111", 
        "5411111111111111", "5511111111111111"
    })
    @DisplayName("fromCardNumber: debería identificar correctamente tarjetas MASTERCARD")
    void fromCardNumber_shouldIdentifyMastercardCards(String cardNumber) {
        assertEquals(BrandType.MASTERCARD, BrandType.fromCardNumber(cardNumber));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"371111111111111", "341111111111111"})
    @DisplayName("fromCardNumber: debería identificar correctamente tarjetas AMEX")
    void fromCardNumber_shouldIdentifyAmexCards(String cardNumber) {
        assertEquals(BrandType.AMEX, BrandType.fromCardNumber(cardNumber));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"6011111111111111", "9111111111111111"})
    @DisplayName("fromCardNumber: debería lanzar IllegalArgumentException para marcas de tarjeta desconocidas")
    void fromCardNumber_shouldThrowExceptionForUnknownCardBrands(String cardNumber) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> BrandType.fromCardNumber(cardNumber));
        
        assertEquals("Unknown card brand", exception.getMessage());
    }
    
    @ParameterizedTest
    @MethodSource("provideCardNumbersAndExpectedBrands")
    @DisplayName("fromCardNumber: debería mapear correctamente todos los tipos de tarjeta válidos")
    void fromCardNumber_shouldMapAllValidCardTypes(String cardNumber, BrandType expectedBrand) {
        assertEquals(expectedBrand, BrandType.fromCardNumber(cardNumber));
    }
    
    private static Stream<Arguments> provideCardNumbersAndExpectedBrands() {
        return Stream.of(
            Arguments.of("4111111111111111", BrandType.VISA),
            Arguments.of("5111111111111111", BrandType.MASTERCARD),
            Arguments.of("2221000000000000", BrandType.MASTERCARD),
            Arguments.of("371111111111111", BrandType.AMEX)
        );
    }
}