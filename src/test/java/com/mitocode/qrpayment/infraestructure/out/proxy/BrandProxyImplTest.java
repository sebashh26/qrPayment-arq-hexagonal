package com.mitocode.qrpayment.infraestructure.out.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import com.mitocode.qrpayment.domain.model.enums.BrandStatus;
import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizationResult;
import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizedRq;

public class BrandProxyImplTest {
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

		// Espera breve para recopilar el body
		assertTrue(latch.await(1, TimeUnit.SECONDS), "Timeout leyendo el body");
		return new String(baos.toByteArray(), StandardCharsets.UTF_8);
	}

	@Test
	@DisplayName("authorized: 200 OK parsea JSON y retorna BrandAuthorizationResult")
	void authorized_success() throws Exception {
		HttpClient httpClient = mock(HttpClient.class);
		BrandProxyImpl proxy = new BrandProxyImpl(httpClient);

		// Respuesta simulada de la marca
		HttpResponse<String> response = mock(HttpResponse.class);
		when(response.statusCode()).thenReturn(200);
		LocalDateTime at = LocalDateTime.of(2025, 8, 7, 10, 0, 0);
		String json = String.format(
				"{\"brandId\":\"b-1\",\"status\":\"%s\",\"failedMessage\":\"\",\"authorizedAt\":\"%s\"}",
				BrandStatus.AUTHORIZED.name(), at.toString());
		when(response.body()).thenReturn(json);

		// Capturamos el request para validar método, headers, URI y body
		ArgumentCaptor<HttpRequest> reqCaptor = ArgumentCaptor.forClass(HttpRequest.class);
		// Stubbing genérico del send
		when(httpClient.send(reqCaptor.capture(), any(HttpResponse.BodyHandler.class))).thenReturn(response);

		BrandAuthorizedRq request = new BrandAuthorizedRq("4111111111111111", "12", "2030", "123");

		BrandAuthorizationResult result = proxy.authorizePayment(request);

		assertNotNull(result);
		assertEquals("b-1", result.getBrandId());
		assertEquals(BrandStatus.AUTHORIZED, result.getStatus());
		assertEquals("", result.getFailedMessage());
		assertEquals(at, result.getAuthorizedAt());

		HttpRequest sent = reqCaptor.getValue();
		assertEquals(URI.create("https://brand-proxy.free.beeceptor.com"), sent.uri());
		assertEquals("POST", sent.method());
		assertEquals("application/json", sent.headers().firstValue("Content-Type").orElse(null));

		String body = extractBody(sent);
		// Verificamos que el JSON contiene campos esperados (no validamos formato
		// estricto)
		assertTrue(body.contains("\"cardNumber\": \"4111111111111111\""));
		assertTrue(body.contains("\"expirationMonth\": \"12\""));
		assertTrue(body.contains("\"expirationYear\": \"2030\""));
		assertTrue(body.contains("\"cvv\": \"123\""));
	}

	@Test
	@DisplayName("authorized: status != 200 lanza RuntimeException con código")
	void authorized_non200_throws() throws Exception {
		HttpClient httpClient = mock(HttpClient.class);
		BrandProxyImpl proxy = new BrandProxyImpl(httpClient);

		HttpResponse<String> response = mock(HttpResponse.class);
		when(response.statusCode()).thenReturn(400);
		when(response.body()).thenReturn("{\"message\":\"bad\"}");

		when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

		RuntimeException ex = assertThrows(RuntimeException.class,
				() -> proxy.authorizePayment(new BrandAuthorizedRq("4111111111111", "01", "2030", "999")));
		assertTrue(ex.getMessage().contains("HTTP error code : 400"));
	}

	@Test
	@DisplayName("authorized: IOException se envuelve en RuntimeException")
	void authorized_ioException_wrapped() throws Exception {
		HttpClient httpClient = mock(HttpClient.class);
		BrandProxyImpl proxy = new BrandProxyImpl(httpClient);

		when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
				.thenThrow(new IOException("network-err"));

		RuntimeException ex = assertThrows(RuntimeException.class,
				() -> proxy.authorizePayment(new BrandAuthorizedRq("4111111111111", "01", "2030", "999")));
		assertTrue(ex.getCause() instanceof IOException);
	}

	@Test
	@DisplayName("authorized: InterruptedException se envuelve en RuntimeException")
	void authorized_interruptedException_wrapped() throws Exception {
		HttpClient httpClient = mock(HttpClient.class);
		BrandProxyImpl proxy = new BrandProxyImpl(httpClient);

		when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
				.thenThrow(new InterruptedException("interrupted"));

		RuntimeException ex = assertThrows(RuntimeException.class,
				() -> proxy.authorizePayment(new BrandAuthorizedRq("4111111111111", "01", "2030", "999")));
		assertTrue(ex.getCause() instanceof InterruptedException);
	}
}
