package com.mitocode.qrpayment.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

class PaymentTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder()
                .merchantId("M123")
                .qrId("QR123")
                .walletId("W123")
                .status(PaymentStatus.AUTHORIZED)
                .authorizationInfo(new AuthorizationInfo())
                .build();
    }

    @Test
    void validateAmount_shouldThrowException_whenAmountIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> payment.validateAmount(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> payment.validateAmount(BigDecimal.valueOf(-10)));
    }

    @Test
    void validateAmount_shouldPass_whenAmountIsPositive() {
        assertDoesNotThrow(() -> payment.validateAmount(BigDecimal.valueOf(100)));
    }

    @Test
    void validateRequiredFields_shouldThrowException_whenMerchantIdIsMissing() {
        payment.setMerchantId(null);
        assertThrows(IllegalArgumentException.class, () -> payment.validateRequiredFields());
    }

    @Test
    void validateRequiredFields_shouldThrowException_whenQrIdIsMissing() {
        payment.setQrId("");
        assertThrows(IllegalArgumentException.class, () -> payment.validateRequiredFields());
    }

    @Test
    void validateRequiredFields_shouldThrowException_whenStatusIsMissing() {
        payment.setStatus(null);
        assertThrows(IllegalArgumentException.class, () -> payment.validateRequiredFields());
    }

    @Test
    void validateRequiredFields_shouldThrowException_whenWalletIdIsMissing() {
        payment.setWalletId(null);
        assertThrows(IllegalArgumentException.class, () -> payment.validateRequiredFields());
    }

    @Test
    void refundPayment_shouldUpdateStatusToRefunded_whenAuthorized() {
        payment.refundPayment();
        assertEquals(PaymentStatus.REFUNDED, payment.getStatus());
        assertNotNull(payment.getAuthorizationInfo().getRefundedAt());
    }

    @Test
    void refundPayment_shouldThrowException_whenNotAuthorized() {
        payment.setStatus(PaymentStatus.DENIED);
        assertThrows(IllegalStateException.class, () -> payment.refundPayment());
    }

    @Test
    void generatePaymentId_shouldGenerateUuid_whenPaymentIdIsNull() {
        payment.setPaymentId(null);
        payment.generatePaymentId();
        assertNotNull(payment.getPaymentId());
    }

    @Test
    void generatePaymentId_shouldNotChangePaymentId_whenAlreadySet() {
        String existingId = "PAY123";
        payment.setPaymentId(existingId);
        payment.generatePaymentId();
        assertEquals(existingId, payment.getPaymentId());
    }
}
