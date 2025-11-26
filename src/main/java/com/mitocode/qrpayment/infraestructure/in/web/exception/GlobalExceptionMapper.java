package com.mitocode.qrpayment.infraestructure.in.web.exception;

import com.mitocode.qrpayment.application.exception.BusinessException;

import com.mitocode.qrpayment.domain.model.exception.MerchantInvalidateException;
import com.mitocode.qrpayment.domain.model.exception.QRInvalidException;
import com.mitocode.qrpayment.domain.model.exception.WalletException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**- @Provider: Es una anotación de JAX-RS que registra la clase como un componente que puede ser usado por el framework.
- En este caso, indica que GlobalExceptionMapper es un manejador de excepciones que debe ser reconocido por el contenedor (como Jersey, RESTEasy, etc.).
- ExceptionMapper<T> es una interfaz de JAX-RS que permite interceptar excepciones lanzadas durante el procesamiento de una solicitud HTTP.
- Al implementar ExceptionMapper<Throwable>, esta clase captura cualquier excepción no manejada (porque Throwable es la superclase de todas las excepciones y errores en Java).
- Esto permite centralizar el manejo de errores y devolver respuestas HTTP personalizadas.

 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		// ex.printStackTrace(); // útil en desarrollo

		String errorType;
		Response.Status status;

//        if (ex instanceof BusinessException) {
//            errorType = "Business Error";
//            status = Response.Status.BAD_REQUEST;
//        } else if (ex instanceof MerchantInvalidateException) {
//            errorType = "Merchant Error";
//            status = Response.Status.BAD_REQUEST;
//        } else if (ex instanceof QRInvalidException) {
//            errorType = "QR Error";
//            status = Response.Status.BAD_REQUEST;
//        } else if (ex instanceof WalletException) {
//            errorType = "Wallet Error";
//            status = Response.Status.BAD_REQUEST;
//        } else {
//            errorType = "Internal Server Error";
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//        }

//		switch (ex) {
//		case BusinessException be -> {
//			errorType = "Business Error";
//			status = Response.Status.BAD_REQUEST;
//		}
//		case MerchantInvalidateException me -> {
//			errorType = "Merchant Error";
//			status = Response.Status.BAD_REQUEST;
//		}
//		case QRInvalidException qe -> {
//			errorType = "QR Error";
//			status = Response.Status.BAD_REQUEST;
//		}
//		case WalletException we -> {
//			errorType = "Wallet Error";
//			status = Response.Status.BAD_REQUEST;
//		}
//		default -> {
//			errorType = "Internal Server Error";
//			status = Response.Status.INTERNAL_SERVER_ERROR;
//		}
//		}

		errorType = switch (ex) {
		case BusinessException be -> "Business Error";
		case MerchantInvalidateException me -> "Merchant Error";
		case QRInvalidException qe -> "QR Error";
		case WalletException we -> "Wallet Error";
		default -> "Internal Server Error";
		};

		status = switch (ex) {
		case BusinessException be -> Response.Status.BAD_REQUEST;
		case MerchantInvalidateException me -> Response.Status.BAD_REQUEST;
		case QRInvalidException qe -> Response.Status.BAD_REQUEST;
		case WalletException we -> Response.Status.BAD_REQUEST;
		default -> Response.Status.INTERNAL_SERVER_ERROR;
		};

		ErrorResponse errorResponse = new ErrorResponse(errorType, ex.getMessage());

		return Response.status(status).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
