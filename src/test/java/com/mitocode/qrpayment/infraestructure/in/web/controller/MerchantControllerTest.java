package com.mitocode.qrpayment.infraestructure.in.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.CreateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.UpdateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.MerchantResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.MerchantService;


@ExtendWith(MockitoExtension.class)
class MerchantControllerTest {

    @Mock
    private MerchantService merchantService;

    @InjectMocks
    private MerchantController merchantController;

//    @Test
//    void testCreateMerchant() {
//        CreateMerchantRequest request =
//                new CreateMerchantRequest(
//                        "http://csl", "test@tes.com", "name", MerchantType.DIGITAL
//                );
//        MerchantResponse responseMock = new MerchantResponse();
//        when(merchantService.createMerchant(request)).thenReturn(responseMock);
//
//        Response response = merchantController.createMerchant(request);
//
//        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
//        assertEquals(responseMock, response.getEntity());
//    }
    
    



//    @Test
//    void testUpdateMerchant() {
//        UpdateMerchantRequest request = new UpdateMerchantRequest();
//        MerchantResponse responseMock = new MerchantResponse();
//        when(merchantService.updateMerchant(request)).thenReturn(responseMock);
//
//        Response response = merchantController.updateMerchant(request);
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        assertEquals(responseMock, response.getEntity());
//    }
//
//    @Test
//    void testFindById() {
//        String merchantId = "123";
//        MerchantResponse responseMock = new MerchantResponse();
//        when(merchantService.getMerchant(merchantId)).thenReturn(responseMock);
//
//        Response response = merchantController.findMerchantById(merchantId);
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        assertEquals(responseMock, response.getEntity());
//    }
//
//    @Test
//    void testFindAll() {
//        List<MerchantResponse> responseMock = List.of(new MerchantResponse(), new MerchantResponse());
//        when(merchantService.getAllMerchants()).thenReturn(responseMock);
//
//        Response response = merchantController.findAllMerchants();
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        assertEquals(responseMock, response.getEntity());
//    }
//
//    @Test
//    void testDeleteById() {
//        String merchantId = "123";
//        MerchantResponse responseMock = new MerchantResponse();
//        when(merchantService.deleteMerchant(merchantId)).thenReturn(responseMock);
//
//        Response response = merchantController.deleteMerchantById(merchantId);
//
//        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//        assertEquals(responseMock, response.getEntity());
//    }
}