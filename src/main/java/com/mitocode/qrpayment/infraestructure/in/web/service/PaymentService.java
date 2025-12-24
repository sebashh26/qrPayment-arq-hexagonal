package com.mitocode.qrpayment.infraestructure.in.web.service;

import org.springframework.stereotype.Service;

import com.mitocode.qrpayment.application.command.PaymentCommand;
import com.mitocode.qrpayment.application.dto.PaymentDto;
import com.mitocode.qrpayment.application.usecase.authorize.AuthorizedQRUseCase;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.PaymentRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.PaymentResponse;
import com.mitocode.qrpayment.infraestructure.in.web.mapper.PaymentDtoMapper;
import com.mitocode.qrpayment.infraestructure.in.web.mapper.PaymentRequestMapper;

@Service
public class PaymentService {
	
	private final AuthorizedQRUseCase authQRUseCase;

    public PaymentService(AuthorizedQRUseCase authQRUseCase) {
        this.authQRUseCase = authQRUseCase;
    }

    public PaymentResponse authorize(PaymentRequest request) {
        PaymentCommand command = PaymentRequestMapper.toCommand(request);
        PaymentDto payDto = authQRUseCase.execute(command);
        return PaymentDtoMapper.toResponse(payDto);
    }

}
