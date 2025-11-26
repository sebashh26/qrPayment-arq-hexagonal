package com.mitocode.qrpayment.infraestructure.in.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.mitocode.qrpayment.application.dto.QRDto;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.QRResponse;

public class QRDtoMapper {
	
	 private QRDtoMapper() {}

	    public static QRResponse toResponse(QRDto dto) {
	        QRResponse response = new QRResponse();
	        response.setId(dto.getId());
	        response.setQrData(dto.getQrData());
	        response.setAmount(dto.getAmount());
	        response.setCurrency(dto.getCurrency());
	        response.setType(dto.getType());
	        response.setQrImage(dto.getQrImage());
	        return response;
	    }

	    public static List<QRResponse> toResponseList(List<QRDto> dtoList) {
	        return dtoList.stream()
	                .map(QRDtoMapper::toResponse)
	                .collect(Collectors.toList());
	    }

}
