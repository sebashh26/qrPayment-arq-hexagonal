package com.mitocode.qrpayment.infraestructure.out.proxy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import org.json.JSONObject;

import com.mitocode.qrpayment.domain.model.enums.BrandStatus;
import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizationResult;
import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizedRq;
import com.mitocode.qrpayment.domain.port.out.proxy.BrandProxy;

public class BrandProxyImpl implements BrandProxy {

	private static final String ENDPOINT = "https://brand-proxy.free.beeceptor.com";
	//ver aqui ; https://app.beeceptor.com/console/brand-proxy

	private final HttpClient httpClient;

	public BrandProxyImpl(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public BrandAuthorizationResult authorizePayment(BrandAuthorizedRq brandAuthorized) {
		String json = """
				{
					"cardNumber": "%s",
					"cvv": "%s",
					"expirationMonth": "%s",
					"expirationYear": "%s"

				}
				""".formatted(brandAuthorized.getCardNumber(), brandAuthorized.getCvv(),
				brandAuthorized.getExpirationMonth(), brandAuthorized.getExpirationYear());

		HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(ENDPOINT))
				.header("Content-Type", "application/json")
				// .POST(HttpRequest.BodyPublishers.ofString(serializePaymentConfirmation(paymentConfirmation)))
				.POST(HttpRequest.BodyPublishers.ofString(json)) // Placeholder for body
				.build();

		HttpResponse<String> brandRslt;
		try {
			brandRslt = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

			if (brandRslt.statusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + brandRslt.statusCode());
			}

			var jsonResult = new JSONObject(brandRslt.body());
			return new BrandAuthorizationResult(jsonResult.getString("brandId"),
					BrandStatus.valueOf(jsonResult.getString("status")), jsonResult.getString("failedMessage"),
					LocalDateTime.parse(jsonResult.getString("authorizedAt")));
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
