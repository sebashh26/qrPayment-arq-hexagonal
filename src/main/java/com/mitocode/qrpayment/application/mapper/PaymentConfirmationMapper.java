package com.mitocode.qrpayment.application.mapper;

import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;

public class PaymentConfirmationMapper {


    public static PaymentConfirmation toConfirmation(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
//solo envio los datos necesarios para la confirmacion del pago
        return new PaymentConfirmation(
                payment.getPaymentId(),
                payment.getPurchaseOrderId(),
                payment.getAmount(),
                payment.getCurrency().getCurrencyCode(),
                payment.getAuthorizedAt(),
                payment.getStatus().name()
        );
    }
}
