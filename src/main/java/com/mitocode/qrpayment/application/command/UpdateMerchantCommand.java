package com.mitocode.qrpayment.application.command;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;

public class UpdateMerchantCommand {

    private String merchantId;
    private String name;
    private MerchantType type;
    private String callbackUrl;

    public UpdateMerchantCommand(String merchantId, String callbackUrl, String name, MerchantType type) {
        this.callbackUrl = callbackUrl;
        this.name = name;
        this.type = type;
    }


    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getName() {
        return name;
    }

    public MerchantType getType() {
        return type;
    }
}