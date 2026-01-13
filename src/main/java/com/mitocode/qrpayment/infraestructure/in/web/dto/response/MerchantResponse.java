package com.mitocode.qrpayment.infraestructure.in.web.dto.response;

import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MerchantResponse {

	private String merchantId;
	private String email;
	private String name;
	private MerchantType type;
	private String callBackUrl;
	private MerchantStatus status;

}
