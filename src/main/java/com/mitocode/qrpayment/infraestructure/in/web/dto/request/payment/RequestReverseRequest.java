package com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestReverseRequest {
	
	String qrId;

}
