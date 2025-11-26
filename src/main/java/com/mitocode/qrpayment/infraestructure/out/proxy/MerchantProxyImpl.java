package com.mitocode.qrpayment.infraestructure.out.proxy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;
import com.mitocode.qrpayment.domain.port.out.proxy.MerchantProxy;

public class MerchantProxyImpl implements MerchantProxy {
	
	private static final String ENDPOINT = "https://merchant-proxy.free.beeceptor.com";
	//ver aqui lo config: https://app.beeceptor.com/console/merchant-proxy
	
	private final HttpClient httpClient;
	
	public MerchantProxyImpl(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public void confirmedPayment(PaymentConfirmation paymentConfirmation) {
		
		String json = """ 
		{
			"currency": "%s",
			"paymentId": "%s",
			"purchaseId": "%s",
			"amount": %s,
			"authorizedAt": "%s",
			"status": "%s"
		}
		""".formatted(
			paymentConfirmation.getCurrency(),
			paymentConfirmation.getPaymentId(),
			paymentConfirmation.getPurchaseOrderId(),
			paymentConfirmation.getAmount(),
			paymentConfirmation.getAuthorizedAt(),
			paymentConfirmation.getStatus()
		);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT))
				.header("Content-Type", "application/json")
				//.POST(HttpRequest.BodyPublishers.ofString(serializePaymentConfirmation(paymentConfirmation)))
				.POST(HttpRequest.BodyPublishers.ofString(json)) // Placeholder for body
				.build();
		
		try {
			this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			

	}

}
