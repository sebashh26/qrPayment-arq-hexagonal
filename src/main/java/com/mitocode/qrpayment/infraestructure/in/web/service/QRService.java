package com.mitocode.qrpayment.infraestructure.in.web.service;

import org.springframework.stereotype.Service;

import com.mitocode.qrpayment.application.command.CreateQRCommand;
import com.mitocode.qrpayment.application.dto.QRDto;
import com.mitocode.qrpayment.application.usecase.qr.CreateQRUseCase;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.qr.CreateQRRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.QRResponse;
import com.mitocode.qrpayment.infraestructure.in.web.mapper.QRDtoMapper;
import com.mitocode.qrpayment.infraestructure.in.web.mapper.QRRequestMapper;

@Service
public class QRService {
	
	private final CreateQRUseCase createQRUseCase;

    public QRService(CreateQRUseCase createQRUseCase) {
        this.createQRUseCase = createQRUseCase;
    }

    public QRResponse createQR(CreateQRRequest request) {
        CreateQRCommand command = QRRequestMapper.toCommand(request);
        QRDto qrDto = createQRUseCase.execute(command);
        return QRDtoMapper.toResponse(qrDto);
    }

}
