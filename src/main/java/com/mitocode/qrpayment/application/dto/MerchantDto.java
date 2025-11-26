package com.mitocode.qrpayment.application.dto;

import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;

public class MerchantDto {
    private String merchantId;
    private String email;
    private String name;
    private MerchantType type;
    private String callbackUrl;
    private MerchantStatus status;

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(MerchantStatus status) {
        this.status = status;
    }

    public void setType(MerchantType type) {
        this.type = type;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public MerchantType getType() {
        return type;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public MerchantStatus getStatus() {
        return status;
    }
}
