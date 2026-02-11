package com.mitocode.qrpayment.infraestructure.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.RequestReverseRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.RequestReverseResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.RequestReverseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/request-reverse")
@Tag(name = "Reversas", description = "Reversas management operations")
@RequiredArgsConstructor
public class RequestReverseController {
	
	private final RequestReverseService requestReverseService;
	
	@PostMapping
    @Operation(summary = "Create a request reverse", description = "Creates a request reverse processing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Request reverse created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Business rule violation")
    })
    public ResponseEntity<RequestReverseResponse> create(@Valid @RequestBody RequestReverseRequest request) {
        RequestReverseResponse response = requestReverseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
