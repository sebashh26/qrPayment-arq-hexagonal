package com.mitocode.qrpayment.infraestructure.out.proxy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProxyRequest {
	
	private String currency;
    private String paymentId;
    private String purchaseId;
    private BigDecimal amount;
    private LocalDateTime authorizedAt;
    private PaymentStatus status;

}
