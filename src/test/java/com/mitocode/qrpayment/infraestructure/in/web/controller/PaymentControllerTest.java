package com.mitocode.qrpayment.infraestructure.in.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.PaymentRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.PaymentResponse;
import com.mitocode.qrpayment.infraestructure.in.web.service.PaymentService;



@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {
	
	@Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;


//   s

}
