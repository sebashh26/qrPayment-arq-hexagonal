package com.mitocode.qrpayment.infraestructure.in.web.controller;

import java.util.List;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.CreateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.UpdateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.MerchantResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.MerchantService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



@Path("/api/merchant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MerchantController {
	
	private final MerchantService merchantService;
	
	
	public MerchantController(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	@POST
	public Response createMerchant(CreateMerchantRequest request) {
		MerchantResponse response = merchantService.createMerchant(request);
		return Response.status(Response.Status.CREATED).entity(response).build();
		//return merchantService.createMerchant(request);
	}
	
	@PUT
	public Response updateMerchant(UpdateMerchantRequest request) {
		MerchantResponse response = merchantService.updateMerchant(request);
		return Response.status(Response.Status.OK).entity(response).build();
		//return merchantService.updateMerchant(request);
	}
	
	@GET
	@Path("/{merchantId}")
	public Response findMerchantById(@PathParam("merchantId") String merchantId) {
		MerchantResponse response = merchantService.getMerchant(merchantId);
		return Response.status(Response.Status.OK).entity(response).build();
		//return merchantService.getMerchant(merchantId);
	}
	
	@GET
	public Response findAllMerchants() {
		// Implementation for fetching all merchants would go here
		List<MerchantResponse> merchants = merchantService.getAllMerchants();
		return Response.status(Response.Status.OK).entity(merchants).build();
		//return merchantService.getAllMerchants();
	}
	
	@DELETE
	@Path("/{merchantId}")
	public Response deleteMerchantById(@PathParam("merchantId") String merchantId) {
		MerchantResponse response = merchantService.deleteMerchant(merchantId);
		return Response.status(Response.Status.NO_CONTENT).entity(response).build();
		//return merchantService.deleteMerchant(merchantId);
	}
	

}
