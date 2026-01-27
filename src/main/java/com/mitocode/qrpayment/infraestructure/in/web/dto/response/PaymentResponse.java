package com.mitocode.qrpayment.infraestructure.in.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentResponse {
	
	private String id;
    private String qrCodeId;
    private String walletId;
    private String merchantId;
    private String purchaseOrderNumber;
    private BigDecimal amount;
    private CurrencyCode currency;
    private PaymentStatus status;
    private LocalDateTime authorizedAt;
    private LocalDateTime refundedAt;

    
}
