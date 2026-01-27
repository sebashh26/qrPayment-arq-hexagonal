package com.mitocode.qrpayment.domain.model.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationInfo {
	
	private LocalDateTime authorizedAt;
    private String failureReason;
    private LocalDateTime refundedAt;

   

}
