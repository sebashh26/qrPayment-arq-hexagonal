package com.mitocode.qrpayment.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.domain.model.vo.QRData;

public class QRDataTest {
	@Test
	@DisplayName("from: parsea correctamente un rawData válido separado por ':'")
	void from_parses_valid() {
		// positions: [0]=tag, [1]=id, [3]=merchant, [5]=po, [7]=amount, [9]=currency,
		// [11]=type
		String raw = "qr:id:x:merchant-1:x:PO-123:x:10.50:x:USD:x:DYNAMIC";

		QRData data = QRData.from(raw);
		assertEquals("id", data.getId());
		assertEquals("merchant-1", data.getMerchantId());
		assertEquals("PO-123", data.getPurchaseOrder());
		assertEquals(0, new BigDecimal("10.50").compareTo(data.getAmount()));
		assertEquals(CurrencyCode.USD, data.getCurrencyCode());
		assertEquals(QRType.DYNAMIC, data.getType());
	}

	@Test
	@DisplayName("from: formato inválido lanza IllegalArgumentException con mensaje contextual")
	void from_invalid_format_throws() {
		String raw = "invalid:format:too:short";
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> QRData.from(raw));
		assertTrue(ex.getMessage().contains("Failed to parse QR Data " + raw));
	}
}
