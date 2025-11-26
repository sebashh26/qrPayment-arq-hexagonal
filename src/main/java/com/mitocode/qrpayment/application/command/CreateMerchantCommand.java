package com.mitocode.qrpayment.application.command;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;

public class CreateMerchantCommand {

    private String email;
    private String name;
    private MerchantType type;
    private String callbackUrl;

    public CreateMerchantCommand(String callbackUrl, String email, String name, MerchantType type) {
        this.callbackUrl = callbackUrl;
        this.email = email;
        this.name = name;
        this.type = type;
    }


    public String getCallbackUrl() {
        return callbackUrl;
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
}