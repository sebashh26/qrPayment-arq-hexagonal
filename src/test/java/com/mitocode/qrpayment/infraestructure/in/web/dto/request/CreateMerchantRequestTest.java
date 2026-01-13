package com.mitocode.qrpayment.infraestructure.in.web.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.CreateMerchantRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CreateMerchantRequestTest {

	private final Validator validator;

	public CreateMerchantRequestTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testCreateMerchantWithoutName() {
		CreateMerchantRequest request = new CreateMerchantRequest("test@example.com", null, MerchantType.DIGITAL,
				"http://callback.url");

		Set<ConstraintViolation<CreateMerchantRequest>> violations = validator.validate(request);

		assertFalse(violations.isEmpty());
		assertEquals("name is required", violations.iterator().next().getMessage());
	}

	@Test
	void testCreateMerchantExceptionWhenNameTooLong() {
		String longName = "a".repeat(101);
		CreateMerchantRequest request = new CreateMerchantRequest("test@example.com", longName, MerchantType.DIGITAL,
				"http://callback.url");

		Set<ConstraintViolation<CreateMerchantRequest>> violations = validator.validate(request);

		assertFalse(violations.isEmpty());
		assertEquals("name must be between 1 and 100 characters", violations.iterator().next().getMessage());
	}

}
