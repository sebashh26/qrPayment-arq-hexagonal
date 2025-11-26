package com.mitocode.qrpayment.infraestructure.out.qr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mitocode.qrpayment.domain.port.out.qr.QRGenerator;

public class QRGeneratorImpl implements QRGenerator {

	@Override
	public byte[] generateImage(String qrData) {


		if (qrData == null || qrData.trim().isEmpty()) {
			throw new IllegalArgumentException("QR data must not be null or empty");
		}

		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			// Se codifica el texto en una matriz de bits () que representa el QR.
			BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 256, 256);

			// Se convierte la matriz de bits en una imagen PNG
			ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
			// Se utiliza la clase MatrixToImageWriter para escribir la imagen en el flujo
			// de salida.
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

			return pngOutputStream.toByteArray();

		} catch (WriterException | IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error generating QR code image", e);
		}
	}

}
