package com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateMerchantRequest {

	//@NotBlank(message = "merchantId is required")
	private String merchantId;
	
	@NotBlank(message = "name is required")
	@Size(min=1, max = 100)
	private String name;
	private MerchantType type;
	private String callBackUrl;
	
		
}
