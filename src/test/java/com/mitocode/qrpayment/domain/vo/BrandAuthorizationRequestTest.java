package com.mitocode.qrpayment.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizedRq;

class BrandAuthorizationRequestTest {
	@Test
    @DisplayName("Constructor: número de tarjeta válido crea instancia y getters devuelven valores")
    void constructor_valid() {
        BrandAuthorizedRq req = new BrandAuthorizedRq("4111111111111", "12", "2030", "123");
        assertEquals("4111111111111", req.getCardNumber());
        assertEquals("12", req.getExpirationMonth());
        assertEquals("2030", req.getExpirationYear());
        assertEquals("123", req.getCvv());
    }

    @Test
    @DisplayName("Constructor: número de tarjeta inválido (<13) lanza IllegalArgumentException")
    void constructor_invalid_cardNumber() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new BrandAuthorizedRq("123", "12", "2030", "123"));
        assertEquals("Invalid card number", ex.getMessage());
    }
}
