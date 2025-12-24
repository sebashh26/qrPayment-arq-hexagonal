package com.mitocode.qrpayment.infraestructure.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr.CreateQRRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.QRResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.QRService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/qr-codes")
@Tag(name = "QR Codes", description = "QR Code management operations")
public class QRController {

    private final QRService qrCodeService;

    public QRController(QRService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping
    @Operation(summary = "Create a new QR Code", description = "Creates a new QR Code for payment processing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "QR Code created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Business rule violation")
    })
    public ResponseEntity<QRResponse> createQRCode(@Valid @RequestBody CreateQRRequest request) {
        QRResponse response = qrCodeService.createQR(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
