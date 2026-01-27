package com.mitocode.qrpayment.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.model.exception.MerchantInvalidateException;

class MerchantTest {
	
	@Test
	 void createMerchantWithValidFieldsTest() {
		// Implement test logic to create a Merchant with valid fields
		Merchant merchant = new Merchant("Merchat test", "test@test.com", MerchantType.DIGITAL, "http://callback.url" );
		assertNotNull(merchant.getMerchantId());
		assertEquals("Merchat test", merchant.getName());
		assertEquals(MerchantStatus.ACTIVE, merchant.getStatus());
	}
	
	@Test
	 void throwExceptionWhenEmailIsNullTest() {
		// Implement test logic to verify exception is thrown when email is null
		try {
			new Merchant("Merchat test", null, MerchantType.DIGITAL, "http://callback.url" );
		} catch (Exception e) {
			assertEquals("email is required", e.getMessage());
		}
	}
	
	@Test
	void  throwExceptionWhenNameIsNull() {
		try {
			new Merchant(null, "test@test.com", MerchantType.DIGITAL, "http://callback.url" );
		} catch (Exception e) {
			assertEquals("name is required", e.getMessage());
		}
		
		Exception exception = assertThrows(MerchantInvalidateException.class, () -> new Merchant(null, "test@test.com", MerchantType.DIGITAL, "http://callback.url" ));
		assertEquals("name is required", exception.getMessage());
	}
	
	@Test
	void  throwExceptionWhenNameIsTooLong() {
		String longName = "a".repeat(101); // Generate a string with 101 characters
		Exception exception = assertThrows(MerchantInvalidateException.class, () -> new Merchant(longName, "test@test.com", MerchantType.DIGITAL, "http://callback.url" ));
		assertEquals("Merchant name can not exceed 100 characters", exception.getMessage());		
	}
	
	@Test
	void desactivateMerchantTest() {
		Merchant merchant = new Merchant("Merchat test", "test@test.com", MerchantType.DIGITAL, "http://callback.url" );
		merchant.desactive();
		assertEquals(MerchantStatus.INACTIVE, merchant.getStatus());
	}

}
