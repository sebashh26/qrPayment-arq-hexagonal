package com.mitocode.qrpayment.infraestructure.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.PaymentRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.PaymentResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments", description = "Payment processing operations")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorize")
    @Operation(summary = "Authorize a payment", description = "Authorizes a payment using QR code and wallet credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment authorized successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Payment authorization failed")
    })
    public ResponseEntity<PaymentResponse> authorizePayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.authorize(request);
        return ResponseEntity.ok(response);
    }
}

