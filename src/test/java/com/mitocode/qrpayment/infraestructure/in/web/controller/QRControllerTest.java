package com.mitocode.qrpayment.infraestructure.in.web.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.infraestructure.in.web.service.QRService;



@ExtendWith(MockitoExtension.class)
class QRControllerTest {

	@Mock
    private QRService qrService;

    @InjectMocks
    private QRController qrController;

//    @Test
//    void testCreateQR() {
//
//        CreateQRRequest request = new CreateQRRequest();
//        QRResponse expectedResponse = new QRResponse();
//
//        when(qrService.createQR(request)).thenReturn(expectedResponse);
//
//        Response response = qrController.createQR(request);
//
//        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
//        assertEquals(expectedResponse, response.getEntity());
//
//        verify(qrService, times(1)).createQR(request);
//    }
}
