package com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateQRRequest {

	@NotEmpty(message = "merchantId is required")
	@NotNull(message = "merchantId is required")
	private String merchantId;
	
	@NotEmpty(message = "purchaseOrder is required")
	@NotNull(message = "purchaseOrder is required")
	private String purchaseOrder;
	
	@NotNull(message = "qrtype is required")
	private QRType qrtype;
	private CurrencyCode currencyCode;
	private BigDecimal amount;
	
	@NotNull(message = "expirateDate is required")
	@Future(message = "expirateDate must be a future date")
	private LocalDateTime expirateDate;
	
//	private void validateExpirateDate(LocalDateTime expirateDate) {
//		if (expirateDate.isBefore(LocalDateTime.now())) {
//			throw new IllegalArgumentException("expirateDate must be a future date");
//		}
//	}
	
}
