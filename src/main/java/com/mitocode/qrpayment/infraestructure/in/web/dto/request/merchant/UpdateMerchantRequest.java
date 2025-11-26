package com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.infraestructure.in.web.exception.InvalidArgumentException;

public class UpdateMerchantRequest {

	private String merchantId;
	private String name;
	private MerchantType type;
	private String callBackUrl;

	public UpdateMerchantRequest(String merchantId ,String callBackUrl,String name, MerchantType type) {
		this.validateMerchantId(merchantId);
		this.validateName(name);
		this.name = name;
		this.type = type;
		this.callBackUrl = callBackUrl;

	}

	private void validateMerchantId(String merchantId) {
		if (merchantId == null || merchantId.isEmpty()) {
			throw new InvalidArgumentException("merchantId is required");
		}
	}
	
	private void validateName(String name) {
		if (name == null || name.isEmpty()) {
			throw new InvalidArgumentException("name is required");
		}
		
		if (name.length() > 100) {
			throw new InvalidArgumentException("Merchant name can not exceed 100 characters");
		}
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
