package com.mitocode.qrpayment.infraestructure.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestReverseResponse {
	
	private String code;
    private String description;

}
