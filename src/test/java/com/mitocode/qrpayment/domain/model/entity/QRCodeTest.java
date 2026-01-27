package com.mitocode.qrpayment.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRStatus;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.domain.model.exception.QRInvalidException;

class QRCodeTest {
	
	@Test
    void validateRequiredFields_withNullMerchantId_throwsException() {
        assertThrows(QRInvalidException.class, () ->
                QRCode.validateRequiredFields(QRType.STATIC, "PO", null, LocalDateTime.now().plusMinutes(5)));
    }

    @Test
    void validateRequiredFields_withEmptyPurchaseOrder_throwsException() {
        assertThrows(QRInvalidException.class, () ->
                QRCode.validateRequiredFields(QRType.STATIC, "", "merchant123", LocalDateTime.now().plusMinutes(5)));
    }

    @Test
    void validateRequiredFields_withNullType_throwsException() {
        assertThrows(QRInvalidException.class, () ->
                QRCode.validateRequiredFields(null, "PO", "merchant123", LocalDateTime.now().plusMinutes(5)));
    }

    @Test
    void validateRequiredFields_withNullExpiration_throwsException() {
        assertThrows(QRInvalidException.class, () ->
                QRCode.validateRequiredFields(QRType.STATIC, "PO", "merchant123", null));
    }

    @Test
    void validateExpirateDate_withPastDate_throwsException() {
        LocalDateTime pastDate = LocalDateTime.now().minusMinutes(1);
        assertThrows(QRInvalidException.class, () -> QRCode.validateExpirateDate(pastDate));
    }

    @Test
    void constructor_withValidData_initializesCorrectly() {
        LocalDateTime futureDate = LocalDateTime.now().plusMinutes(10);
        QRCode qr = new QRCode(QRType.DYNAMIC, "PO123", "merchant123", futureDate, CurrencyCode.PEN, new BigDecimal("5.00"));

        assertNotNull(qr.getId());
        assertEquals(QRStatus.ACTIVE, qr.getStatus());
        assertTrue(qr.isActive());
        assertNotNull(qr.getQrData());
        assertTrue(qr.getQrData().contains("merchantId:merchant123"));
        assertTrue(qr.getQrData().contains("purchaseOrder:PO123"));
        assertEquals("PO123", qr.getPurchaseOrder());
        assertEquals("merchant123", qr.getMerchantId());
        assertEquals(QRType.DYNAMIC, qr.getType());
        assertEquals(CurrencyCode.PEN, qr.getCurrencyCode());
        assertEquals(new BigDecimal("5.00"), qr.getAmount());
    }

    @Test
    void validate_withInactiveStatus_throwsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusMinutes(10);
        QRCode qr = new QRCode(QRType.STATIC, "PO123", "merchant123", futureDate, CurrencyCode.USD, null);
        qr.setUsedQR();
        assertThrows(QRInvalidException.class, qr::isValidQR);
    }

    @Test
    void validate_withExpiredDate_throwsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusMinutes(1);
        QRCode qr = new QRCode(QRType.STATIC, "PO123", "merchant123", futureDate, CurrencyCode.USD, null);
        qr.setExpirateDate(LocalDateTime.now().minusMinutes(1));
        assertThrows(QRInvalidException.class, qr::isValidQR);
    }

    @Test
    void validate_withActiveAndNotExpired_doesNotThrow() {
        LocalDateTime futureDate = LocalDateTime.now().plusMinutes(5);
        QRCode qr = new QRCode(QRType.STATIC, "PO123", "merchant123", futureDate, CurrencyCode.USD, null);
        qr.isValidQR();
    }

    @Test
    void setUsedQr_changesStatusToUsed() {
        LocalDateTime futureDate = LocalDateTime.now().plusMinutes(5);
        QRCode qr = new QRCode(QRType.STATIC, "PO123", "merchant123", futureDate, CurrencyCode.USD, null);
        qr.setUsedQR();
        assertEquals(QRStatus.USED, qr.getStatus());
        assertFalse(qr.isActive());
    }

    @Test
    void setInactiveQr_changesStatusToInactive() {
        LocalDateTime futureDate = LocalDateTime.now().plusMinutes(5);
        QRCode qr = new QRCode(QRType.STATIC, "PO123", "merchant123", futureDate, CurrencyCode.USD, null);
        qr.setInactiveQR();
        assertEquals(QRStatus.INACTIVE, qr.getStatus());
        assertFalse(qr.isActive());
    }

}
