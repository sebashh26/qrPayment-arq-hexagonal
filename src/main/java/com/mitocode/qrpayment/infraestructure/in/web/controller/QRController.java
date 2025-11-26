package com.mitocode.qrpayment.infraestructure.in.web.controller;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr.CreateQRRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.QRResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.QRService;

import jakarta.mvc.Controller;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Controller
@Path("qr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QRController {

	private final QRService qrService;

	public QRController(QRService qrService) {
		this.qrService = qrService;
	}

	@POST
	public Response createQR(CreateQRRequest request) {
		QRResponse qrResponse = qrService.createQR(request);
		return Response.status(Response.Status.CREATED).entity(qrResponse).build();

	}
}
