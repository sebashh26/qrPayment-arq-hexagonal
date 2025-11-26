package com.mitocode.qrpayment.domain.port.out.persistence;

import java.util.Optional;

import com.mitocode.qrpayment.domain.model.entity.QRCode;

//estos son los puetos de salida (outbound ports) de la aplicacion
public interface QRRepository {

		QRCode save(QRCode qrCode);
		boolean existsQRCode(String  qrId);
		Optional<QRCode> findById(String id);
		QRCode update(QRCode qrCode);
		
}
