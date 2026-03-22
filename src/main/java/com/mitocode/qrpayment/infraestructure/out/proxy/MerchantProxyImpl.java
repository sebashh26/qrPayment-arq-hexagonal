package com.mitocode.qrpayment.infraestructure.out.proxy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;
import com.mitocode.qrpayment.domain.port.out.proxy.MerchantProxy;
import com.mitocode.qrpayment.infraestructure.out.proxy.exception.ProxyException;
import com.mitocode.qrpayment.infraestructure.out.proxy.mapper.MerchantProxyMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MerchantProxyImpl implements MerchantProxy {

	private static final String ENDPOINT = "https://merchant-proxy.free.beeceptor.com";
	// ver aqui lo config: https://app.beeceptor.com/console/merchant-proxy

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	@Override
	public void confirmedPayment(PaymentConfirmation paymentConfirmation) {

//		String json = """
//		{
//			"currency": "%s",
//			"paymentId": "%s",
//			"purchaseId": "%s",
//			"amount": %s,
//			"authorizedAt": "%s",
//			"status": "%s"
//		}
//		""".formatted(
//			paymentConfirmation.getCurrency(),
//			paymentConfirmation.getPaymentId(),
//			paymentConfirmation.getPurchaseOrderId(),
//			paymentConfirmation.getAmount(),
//			paymentConfirmation.getAuthorizedAt(),
//			paymentConfirmation.getStatus()
//		);
		try {
			var requestDto = MerchantProxyMapper.toRequest(paymentConfirmation);
			String json = objectMapper.writeValueAsString(requestDto);

			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ENDPOINT))
					.header("Content-Type", "application/json")
					// .POST(HttpRequest.BodyPublishers.ofString(serializePaymentConfirmation(paymentConfirmation)))
					.POST(HttpRequest.BodyPublishers.ofString(json)) // Placeholder for body
					.build();

			this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			
		} catch (IOException | InterruptedException e) {
			throw new ProxyException(e.getMessage());
		}

	}

}
