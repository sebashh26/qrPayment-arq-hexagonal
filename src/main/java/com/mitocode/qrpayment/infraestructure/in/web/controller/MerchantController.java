package com.mitocode.qrpayment.infraestructure.in.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.qrpayment.infraestructure.in.web.annotation.AuthorizeMerchantFilter;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.CreateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.UpdateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.MerchantResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.MerchantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/merchant")
@Tag(name = "Merchant Controller", description = "Merchant management endpoints")
public class MerchantController {
	
	private final MerchantService merchantService;
	
	
	public MerchantController(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	@PostMapping
    @Operation(summary = "Create a new merchant", description = "Creates a new merchant in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Merchant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Merchant already exists")
    })
	 public ResponseEntity<MerchantResponse> create(@Valid @RequestBody CreateMerchantRequest request){
        MerchantResponse response  = merchantService.createMerchant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@PutMapping("/{id}")
    @Operation(summary = "Update merchant", description = "Updates the information of a merchant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Merchant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Merchant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update data")
    })
	public ResponseEntity<MerchantResponse> update( @PathVariable String id, @Valid @RequestBody UpdateMerchantRequest request){
		request.setMerchantId(id);
        MerchantResponse response =  merchantService.updateMerchant(request);
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/{id}")
    @Operation(summary = "Get merchant by ID", description = "Retrieves a merchant by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Merchant found"),
            @ApiResponse(responseCode = "404", description = "Merchant not found")
    })
    public ResponseEntity<MerchantResponse> findById(@PathVariable("id") String merchantId){
        return ResponseEntity.ok(merchantService.getMerchant(merchantId));
    }
	
	@GetMapping
	@AuthorizeMerchantFilter()
    @Operation(summary = "List all merchants", description = "Retrieves a list of all merchants")
    public ResponseEntity<List<MerchantResponse>> findAll() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }
	
	@DeleteMapping("/{id}")
    @Operation(summary = "Delete merchant", description = "no delete only desactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Merchant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Merchant not found")
    })
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build();
    }
	

}
