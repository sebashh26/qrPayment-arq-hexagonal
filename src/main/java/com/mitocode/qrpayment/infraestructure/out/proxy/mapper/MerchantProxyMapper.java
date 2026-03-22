package com.mitocode.qrpayment.infraestructure.out.proxy.mapper;

import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;
import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;
import com.mitocode.qrpayment.infraestructure.out.proxy.dto.MerchantProxyRequest;

import lombok.experimental.UtilityClass;


@UtilityClass
public class MerchantProxyMapper {
	

    public static MerchantProxyRequest toRequest(PaymentConfirmation confirmation) {
        if (confirmation == null) {
            return null;
        }
        return new MerchantProxyRequest(
                confirmation.getCurrency(),
                confirmation.getPaymentId(),
                confirmation.getPurchaseOrderId(),
                confirmation.getAmount(),
                confirmation.getAuthorizedAt(),
                PaymentStatus.fromDescription(confirmation.getStatus())
        );
    }

}
