package com.mitocode.qrpayment.infraestructure.in.web.controller;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.PaymentRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.PaymentResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.PaymentService;

import jakarta.mvc.Controller;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Controller
@Path("authorize")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {
	
	private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @POST
    public Response authorizePayment(PaymentRequest request) {
        PaymentResponse qrResponse = paymentService.authorize(request);
        return Response.status(Response.Status.OK).entity(qrResponse).build();
    }

}
