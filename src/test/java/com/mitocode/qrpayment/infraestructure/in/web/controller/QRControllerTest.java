package com.mitocode.qrpayment.infraestructure.in.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr.CreateQRRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.QRResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.QRService;

import jakarta.ws.rs.core.Response;

@ExtendWith(MockitoExtension.class)
public class QRControllerTest {

	@Mock
    private QRService qrService;

    @InjectMocks
    private QRController qrController;

    @Test
    void testCreateQR() {

        CreateQRRequest request = new CreateQRRequest();
        QRResponse expectedResponse = new QRResponse();

        when(qrService.createQR(request)).thenReturn(expectedResponse);

        Response response = qrController.createQR(request);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());

        verify(qrService, times(1)).createQR(request);
    }
}
