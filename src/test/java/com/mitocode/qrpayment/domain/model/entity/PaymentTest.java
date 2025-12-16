package com.mitocode.qrpayment.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.domain.model.enums.BrandType;
import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

public class PaymentTest {
	
	 @Test
	    void shouldCreateValidPayment() {
	        Payment payment = new Payment(
	                "merchant-123",
	                "qr-456",
	                new BigDecimal("100.00"),
	                CurrencyCode.PEN,
	                "PO123",
	                PaymentStatus.AUTHORIZED,
	                BrandType.VISA,
	                "wallet-xyz",
	                null,
	                LocalDateTime.now(),
	                null
	        );

	        assertNotNull(payment.getPaymentId());
	        assertEquals(PaymentStatus.AUTHORIZED, payment.getStatus());
	        assertEquals(CurrencyCode.PEN, payment.getCurrency());
	    }

	    @Test
	    void shouldThrowExceptionIfAmountIsNegative() {
	        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
	                new Payment(
	                        "merchant-123",
	                        "qr-456",
	                        new BigDecimal("-50.00"),
	                        CurrencyCode.PEN,
	                        "PO123",
	                        PaymentStatus.AUTHORIZED,
	                        BrandType.VISA,
	                        "wallet-xyz",
	                        null,
	                        LocalDateTime.now(),
	                        null
	                )
	        );

	        assertEquals("Amount must be greater than zero", exception.getMessage());
	    }

	    @Test
	    void shouldThrowExceptionIfRequiredFieldIsNull() {
	        // Ejemplo con merchantId null
	        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
	                new Payment(
	                        null,
	                        "qr-456",
	                        new BigDecimal("100.00"),
	                        CurrencyCode.PEN,
	                        "PO123",
	                        PaymentStatus.AUTHORIZED,
	                        BrandType.VISA,
	                        "wallet-xyz",
	                        null,
	                        LocalDateTime.now(),
	                        null
	                )
	        );

	        assertEquals("Merchant id is required", exception.getMessage());
	    }

	    @Test
	    void shouldUpdateStatusToRefundWhenRefundPaymentIsCalled() {
	        Payment payment = new Payment(
	                "merchant-123",
	                "qr-456",
	                new BigDecimal("100.00"),
	                CurrencyCode.PEN,
	                "PO123",
	                PaymentStatus.AUTHORIZED,
	                BrandType.VISA,
	                "wallet-xyz",
	                null,
	                LocalDateTime.now(),
	                null
	        );

	        payment.refundPayment();

	        assertEquals(PaymentStatus.REFUNDED, payment.getStatus());
	    }

}
