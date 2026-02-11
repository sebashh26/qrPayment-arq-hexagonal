package com.mitocode.qrpayment.infraestructure.in.web.service;

import org.springframework.stereotype.Service;

import com.mitocode.qrpayment.application.usecase.requestReverse.CreateRequestReverseUseCase;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.payment.RequestReverseRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.RequestReverseResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RequestReverseService {
	
	private final static String CODE_SUCESS = "01";
    private final static String MESSAGE_SUCESS = "Solicitud de Reversa creada con exito";


    private final CreateRequestReverseUseCase createRequestReverseUseCase;

    

    public RequestReverseResponse create(RequestReverseRequest request) {
        createRequestReverseUseCase.execute(request.getQrId());

        RequestReverseResponse requestReverseResponse = new RequestReverseResponse();
        requestReverseResponse.setCode(CODE_SUCESS);
        requestReverseResponse.setDescription(MESSAGE_SUCESS);

        return requestReverseResponse;
    }

}
