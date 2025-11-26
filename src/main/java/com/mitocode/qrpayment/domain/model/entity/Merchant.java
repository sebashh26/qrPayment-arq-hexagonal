package com.mitocode.qrpayment.domain.model.entity;

import java.util.UUID;

import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.model.exception.MerchantInvalidateException;

public class Merchant {

    private String merchantId;
    private String email;
    private String name;
    private MerchantType type;
    private String callBackUrl;
    private MerchantStatus status;
	
    public Merchant( String name, String email, MerchantType type,String callBackUrl) {
    	this.validateRequiredField(email, name, type);
    	this.validateName(name);
    	
        this.email = email;
        this.name = name;
        this.type = type;
        this.callBackUrl = callBackUrl;
        this.merchantId = UUID.randomUUID().toString();
        this.status = MerchantStatus.ACTIVE;
        
    }
    private void validateRequiredField(String email, String name, MerchantType type){
        if (email== null || email.isEmpty()){
            throw new MerchantInvalidateException("email is required");
        }
        if (name == null || name.isEmpty()){
            throw  new MerchantInvalidateException("name is required");
        }
        if (type == null){
            throw new MerchantInvalidateException("type is required");
        }
    }

    private void validateName(String name){
        if (name.length()>100){
            throw new MerchantInvalidateException("Merchant name can not exceed 100 characters");
        }
    }

    
    public void desactive(){
        this.status = MerchantStatus.INACTIVE;
    }
    
	public boolean isActive() {
		return this.status == MerchantStatus.ACTIVE;
	}
	
	public void validate() {
        if (!this.isActive()) {
			throw new MerchantInvalidateException("Merchant is inactive");
		}
    }

    public void update(String email, String name, MerchantType type, String callBackUrl) {
		this.validateRequiredField(email, name, type);
		this.validateName(name);
		this.email = email;
		this.name = name;
		this.type = type;
		this.callBackUrl = callBackUrl;
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
