package com.mitocode.qrpayment.application.mapper;

import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.application.dto.QRDto;
import com.mitocode.qrpayment.domain.model.entity.QRCode;

@Component
public class QRCodeToQRDto {

	public QRDto buildQRDto(QRCode qrCode) {
		QRDto qrResponse = new QRDto();
		qrResponse.setId(qrCode.getId());
		qrResponse.setType(qrCode.getQrtype());
		qrResponse.setCurrency(qrCode.getCurrencyOrder());
		qrResponse.setAmount(qrCode.getAmount());
		qrResponse.setQrData(qrCode.getQrData());
		qrResponse.setQrImage(qrCode.getQrImage());
		return qrResponse;
	}
}
