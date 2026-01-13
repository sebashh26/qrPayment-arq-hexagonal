package com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateMerchantRequest {

	@NotEmpty(message = "email is required")
	@NotBlank(message = "email is required")
	@Email
	private String email;
	@NotEmpty(message = "name is required")
	@NotBlank(message = "name is required")
	@Size(min=1, max = 100, message = "name must be between 1 and 100 characters")
	private String name;
	
	@NotNull(message = "type is required")
	private MerchantType type;
	private String callBackUrl;
}
