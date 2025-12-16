package com.mitocode.qrpayment.infraestructure.in.web.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.CreateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.exception.InvalidArgumentException;

public class CreateMerchantRequestTest {
	
	 @Test
	    void testCreateMerchantWithoutName() {
	        Exception exception = assertThrows(InvalidArgumentException.class, () ->
	                new CreateMerchantRequest(
	                        "http://csl", "test@tes.com", null, MerchantType.DIGITAL
	                )
	        );
	        assertEquals("name is required" , exception.getMessage());
	    }

	    @Test
	    void testCreateMerchantExceptionWhenNameTooLong() {
	        String longName = "a".repeat(101);
	        Exception exception = assertThrows(InvalidArgumentException.class, () ->
	                new CreateMerchantRequest("http://callback.url", "test@example.com", longName ,  MerchantType.DIGITAL)
	        );
	        assertEquals("Merchant name can not exceed 100 characters" , exception.getMessage());
	    }

}
