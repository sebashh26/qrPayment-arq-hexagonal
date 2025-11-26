package com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.infraestructure.in.web.exception.InvalidArgumentException;

public class CreateMerchantRequest {

	private String email;
	private String name;
	private MerchantType type;
	private String callBackUrl;
	
	

	public CreateMerchantRequest() {
	}

	public CreateMerchantRequest(String callBackUrl, String email, String name, MerchantType type) {
		this.validateRequiredField(email, name, type);
		this.validateName(name);
		this.email = email;
		this.name = name;
		this.type = type;
		this.callBackUrl = callBackUrl;

	}

	private void validateRequiredField(String email, String name, MerchantType type) {
		if (email == null || email.isEmpty()) {
			throw new InvalidArgumentException("email is required");
		}
		if (name == null || name.isEmpty()) {
			throw new InvalidArgumentException("name is required");
		}
		if (type == null) {
			throw new InvalidArgumentException("type is required");
		}
	}

	private void validateName(String name) {
		if (name.length() > 100) {
			throw new InvalidArgumentException("Merchant name can not exceed 100 characters");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MerchantType getType() {
		return type;
	}

	public void setType(MerchantType type) {
		this.type = type;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	
	

}
