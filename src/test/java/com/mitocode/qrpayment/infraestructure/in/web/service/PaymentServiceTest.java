package com.mitocode.qrpayment.infraestructure.in.web.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.application.dto.PaymentDto;
import com.mitocode.qrpayment.application.usecase.authorize.AuthorizedQRUseCase;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.PaymentRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.PaymentResponse;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
	
	@Mock
    private AuthorizedQRUseCase useCase;
	
	@InjectMocks
	private PaymentService service;
	
	@Test
    void authorize_shouldReturnPaymentResponse() {
		

        //otra forma de mockear sin usar la anotacion @Mock
        PaymentRequest request = mock(PaymentRequest.class);
        PaymentDto paymentDto = mock(PaymentDto.class);
        when(useCase.execute(any())).thenReturn(paymentDto);

        PaymentResponse response = service.authorize(request);
        assertNotNull(response);
    }

}
