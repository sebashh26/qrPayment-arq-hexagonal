package com.mitocode.qrpayment.infraestructure.out.qr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRGeneratorImplTest {
	@Test
	@DisplayName("generateImage: retorna PNG válido de 256x256")
	void generateImage_success_png() throws Exception {
		QRGeneratorImpl generator = new QRGeneratorImpl();
		byte[] bytes = generator.generateImage("HELLO-WORLD");

		assertNotNull(bytes);

		var img = ImageIO.read(new ByteArrayInputStream(bytes));
		assertNotNull(img, "La imagen debería poder decodificarse");
		assertEquals(256, img.getWidth());
		assertEquals(256, img.getHeight());
	}

	@Test
	@DisplayName("generateImage: WriterException es envuelta en RuntimeException")
	void generateImage_writerException_wrapped() {
		// Mockear la construcción de QRCodeWriter para forzar WriterException en
		// encode()
		try (MockedConstruction<com.google.zxing.qrcode.QRCodeWriter> construction = Mockito
				.mockConstruction(com.google.zxing.qrcode.QRCodeWriter.class, (mock, context) -> {
					when(mock.encode(anyString(), any(), anyInt(), anyInt()))
							.thenThrow(new WriterException("fail-encode"));
				})) {

			QRGeneratorImpl generator = new QRGeneratorImpl();
			RuntimeException ex = assertThrows(RuntimeException.class, () -> generator.generateImage("DATA"));
			assertTrue(ex.getCause() instanceof WriterException);
		}
	}

	@Test
	@DisplayName("generateImage: IOException de MatrixToImageWriter.writeToStream es envuelta en RuntimeException")
	void generateImage_ioException_wrapped() throws Exception {
		BitMatrix matrix = new BitMatrix(256, 256);

		// Mockear la construcción de QRCodeWriter para devolver un BitMatrix válido
		try (MockedConstruction<com.google.zxing.qrcode.QRCodeWriter> construction = Mockito
				.mockConstruction(com.google.zxing.qrcode.QRCodeWriter.class, (mock, context) -> {
					when(mock.encode(anyString(), any(), anyInt(), anyInt())).thenReturn(matrix);
				});
				// Mockear estático MatrixToImageWriter.writeToStream para lanzar IOException
				MockedStatic<com.google.zxing.client.j2se.MatrixToImageWriter> mtw = Mockito
						.mockStatic(com.google.zxing.client.j2se.MatrixToImageWriter.class)) {

			mtw.when(() -> com.google.zxing.client.j2se.MatrixToImageWriter.writeToStream(eq(matrix), eq("PNG"), any()))
					.thenThrow(new IOException("io-fail"));

			QRGeneratorImpl generator = new QRGeneratorImpl();
			RuntimeException ex = assertThrows(RuntimeException.class, () -> generator.generateImage("DATA"));
			assertTrue(ex.getCause() instanceof IOException);
		}
	}
}
