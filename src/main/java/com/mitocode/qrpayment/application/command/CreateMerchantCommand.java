package com.mitocode.qrpayment.application.command;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMerchantCommand {

    private String email;
    private String name;
    private MerchantType type;
    private String callbackUrl;

}