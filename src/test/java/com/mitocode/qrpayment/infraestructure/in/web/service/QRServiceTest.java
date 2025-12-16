package com.mitocode.qrpayment.infraestructure.in.web.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.application.dto.QRDto;
import com.mitocode.qrpayment.application.usecase.qr.CreateQRUseCase;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr.CreateQRRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.QRResponse;

public class QRServiceTest {
	
	 @Test
	    void createQR_shouldReturnQRResponse() {
	        CreateQRUseCase useCase = mock(CreateQRUseCase.class);
	        QRService service = new QRService(useCase);

	        CreateQRRequest request = mock(CreateQRRequest.class);
	        QRDto qrDto = mock(QRDto.class);
	        when(useCase.execute(any())).thenReturn(qrDto);

	        QRResponse response = service.createQR(request);
	        assertNotNull(response);
	    }

}
