package com.mitocode.qrpayment.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestReverse {
	
	private String merchantId;
    private String qrId;

}
