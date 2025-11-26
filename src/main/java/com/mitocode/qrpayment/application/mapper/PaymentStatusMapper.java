package com.mitocode.qrpayment.application.mapper;

import static com.mitocode.qrpayment.domain.model.enums.PaymentStatus.DENIED;

import com.mitocode.qrpayment.domain.model.enums.BrandStatus;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

public class PaymentStatusMapper {
    public static PaymentStatus fromBrandStatus(BrandStatus brandStatus) {
        return switch (brandStatus) {
        	//puedo llamar al payment status authorized por el enum o como import static como DENIED
            case AUTHORIZED -> PaymentStatus.AUTHORIZED;
            case DENIED -> DENIED;
            case PENDING -> throw new IllegalStateException("Cannot create a Payment with PENDING status");
        };
    }
}