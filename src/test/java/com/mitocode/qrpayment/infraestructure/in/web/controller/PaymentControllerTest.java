package com.mitocode.qrpayment.infraestructure.in.web.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.infraestructure.in.web.service.PaymentService;



@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {
	
	@Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;




}
