package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchants")
public class MerchantEntity {
	
	@Id
	@Column(name = "merchantid", length = 36)
	private String merchantId;
	@Column(name = "email", nullable = false, length = 255)
    private String email;
    @Column(nullable = false, length = 100)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MerchantType type;
    @Column(name = "callback_url", length = 500)
    private String callBackUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MerchantStatus status;
    
    
    
	public MerchantEntity() {
	}


	public MerchantEntity(String merchantId, String email, String name, MerchantType type, String callBackUrl,
			MerchantStatus status) {
		super();
		this.merchantId = merchantId;
		this.email = email;
		this.name = name;
		this.type = type;
		this.callBackUrl = callBackUrl;
		this.status = status;
	}


	public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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


	public MerchantStatus getStatus() {
		return status;
	}


	public void setStatus(MerchantStatus status) {
		this.status = status;
	}
    
	
	
    

}
