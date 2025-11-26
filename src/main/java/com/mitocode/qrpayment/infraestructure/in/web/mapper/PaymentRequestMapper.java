package com.mitocode.qrpayment.infraestructure.in.web.mapper;

import com.mitocode.qrpayment.application.command.PaymentCommand;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.PaymentRequest;

public class PaymentRequestMapper {

    private PaymentRequestMapper() {}

    public static PaymentCommand toCommand(PaymentRequest request) {
        return new PaymentCommand(
                request.getMerchantId(),
                request.getQrData(),
                request.getAmount(),
                request.getCurrency(),
                request.getPurchaseOrderid(),
                request.getWalletId(),
                request.getCardNumber(),
                request.getExpirationMonth(),
                request.getExpirationYear(),
                request.getCvv()
        );
    }
}
