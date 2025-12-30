package com.mitocode.qrpayment.infraestructure.out.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;

public class MerchantProxyImplTest {
	private static String extractBody(HttpRequest request) throws InterruptedException {
		Optional<HttpRequest.BodyPublisher> opt = request.bodyPublisher();
		assertTrue(opt.isPresent(), "El BodyPublisher debe estar presente");
		HttpRequest.BodyPublisher publisher = opt.get();

		CountDownLatch latch = new CountDownLatch(1);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		publisher.subscribe(new Flow.Subscriber<ByteBuffer>() {
			private Flow.Subscription subscription;

			@Override
			public void onSubscribe(Flow.Subscription subscription) {
				this.subscription = subscription;
				subscription.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(ByteBuffer item) {
				byte[] bytes = new byte[item.remaining()];
				item.get(bytes);
				baos.write(bytes, 0, bytes.length);
			}

			@Override
			public void onError(Throwable throwable) {
				latch.countDown();
			}

			@Override
			public void onComplete() {
				latch.countDown();
			}
		});

		assertTrue(latch.await(1, TimeUnit.SECONDS), "Timeout leyendo el body");
		return new String(baos.toByteArray(), StandardCharsets.UTF_8);
	}

	@Test
	@DisplayName("confirmedPayment: construye POST JSON y envía request; no lanza excepción en 200")
	void confirmedPayment_success() throws Exception {
		HttpClient httpClient = mock(HttpClient.class);
		MerchantProxyImpl proxy = new MerchantProxyImpl(httpClient);

		HttpResponse<String> response = mock(HttpResponse.class);
		when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

		PaymentConfirmation confirmation = mock(PaymentConfirmation.class);
		when(confirmation.getCurrency()).thenReturn("USD");
		when(confirmation.getPaymentId()).thenReturn("pay-1");
		when(confirmation.getPurchaseOrderId()).thenReturn("PO-1");
		when(confirmation.getAmount()).thenReturn(new BigDecimal("10.00"));
		when(confirmation.getAuthorizedAt()).thenReturn(LocalDateTime.of(2025, 8, 7, 11, 0, 0));
		when(confirmation.getStatus()).thenReturn("APPROVED");

		ArgumentCaptor<HttpRequest> reqCaptor = ArgumentCaptor.forClass(HttpRequest.class);

		// Ejecuta
		proxy.confirmedPayment(confirmation);

		// Captura del request real enviado
		verify(httpClient).send(reqCaptor.capture(), any(HttpResponse.BodyHandler.class));
		HttpRequest sent = reqCaptor.getValue();

		assertEquals(URI.create("https://merchant-proxy.free.beeceptor.com"), sent.uri());
		assertEquals("POST", sent.method());
		assertEquals("application/json", sent.headers().firstValue("Content-Type").orElse(null));

		String body = extractBody(sent);
		assertTrue(body.contains("\"currency\": \"USD\""));
		assertTrue(body.contains("\"paymentId\": \"pay-1\""));
		assertTrue(body.contains("\"purchaseId\": \"PO-1\""));
		assertTrue(body.contains("\"amount\": 10.0"));
		assertTrue(body.contains("\"authorizedAt\": \"2025-08-07T11:00\""));
		assertTrue(body.contains("\"status\": \"APPROVED\""));
	}

	@Test
	@DisplayName("confirmedPayment: IOException se envuelve en RuntimeException")
	void confirmedPayment_ioException_wrapped() throws Exception {
		HttpClient httpClient = mock(HttpClient.class);
		MerchantProxyImpl proxy = new MerchantProxyImpl(httpClient);

		when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
				.thenThrow(new IOException("io"));

		PaymentConfirmation confirmation = mock(PaymentConfirmation.class);

		IOException ex = assertThrows(IOException.class, () -> proxy.confirmedPayment(confirmation));
		assertTrue(ex  instanceof IOException);
	}

	@Test
	@DisplayName("confirmedPayment: InterruptedException se envuelve en RuntimeException")
	void confirmedPayment_interruptedException_wrapped() throws Exception {
		HttpClient httpClient = mock(HttpClient.class);
		MerchantProxyImpl proxy = new MerchantProxyImpl(httpClient);

		when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
				.thenThrow(new InterruptedException("inter2"));

		PaymentConfirmation confirmation = mock(PaymentConfirmation.class);

		InterruptedException ex = assertThrows(InterruptedException.class, () -> proxy.confirmedPayment(confirmation));
		assertTrue(ex instanceof InterruptedException);
	}
}
