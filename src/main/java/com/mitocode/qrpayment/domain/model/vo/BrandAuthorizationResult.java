package com.mitocode.qrpayment.domain.model.vo;

import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.BrandStatus;

public class BrandAuthorizationResult {
	
	private final String brandId;
	private final BrandStatus status;
	private final String failedMessage;
	private final LocalDateTime authorizedAt;
	
	public BrandAuthorizationResult(String brandId, BrandStatus status, String failedMessage, LocalDateTime authorizedAt) {
		this.brandId = brandId;
		this.status = status;
		this.failedMessage = failedMessage;
		this.authorizedAt = authorizedAt;
	}

	public String getBrandId() {
		return brandId;
	}

	public BrandStatus getStatus() {
		return status;
	}

	public String getFailedMessage() {
		return failedMessage;
	}

	public LocalDateTime getAuthorizedAt() {
		return authorizedAt;
	}
	
	
	

}
