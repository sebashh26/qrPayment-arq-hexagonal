package com.mitocode.qrpayment.infraestructure.in.web.mapper;

import com.mitocode.qrpayment.application.command.CreateQRCommand;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr.CreateQRRequest;

public class QRRequestMapper {
	
	private QRRequestMapper() {}
    public static CreateQRCommand toCommand(CreateQRRequest request) {
        return new CreateQRCommand(
                request.getMerchantId(),
                request.getPurchaseOrder(),
                request.getQrtype(),
                request.getCurrencyCode(),
                request.getAmount(),
                request.getExpirateDate()
        );
    }

}
