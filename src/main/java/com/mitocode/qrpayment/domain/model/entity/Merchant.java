package com.mitocode.qrpayment.domain;

public class Merchant {

    private String merchantId;
    private String email;
    private String name;
    private String type;
    private String callBackUrl;

    public Merchant(String merchantId, String email, String name, String type, String callBackUrl) {
        this.merchantId = merchantId;
        this.email = email;
        this.name = name;
        this.type = type;
        //this.callBackUrl = callBackUrl;
        this.validateRequiredField(email, name, type);
        this.validateName(name);
    }

    private void validateRequiredField(String email, String name, String type){
        if (email== null || email.isEmpty()){
            throw new IllegalArgumentException("email is required");
        }
        if (name == null || name.isEmpty()){
            throw  new IllegalArgumentException("name is required");
        }
        if (type == null || type.isEmpty()){
            throw new IllegalArgumentException("type is required");
        }
    }

    private void validateName(String name){
        if (name.length()>100){
            throw new IllegalArgumentException("Merchant name can not exceed 100 characters");
        }
    }

}
