package com.mitocode.qrpayment.infraestructure.in.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mitocode.qrpayment.application.command.CreateMerchantCommand;
import com.mitocode.qrpayment.application.command.UpdateMerchantCommand;
import com.mitocode.qrpayment.application.dto.MerchantDto;
import com.mitocode.qrpayment.application.usecase.merchant.CreateMerchantUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.DeleteMerchantUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.GetAllMerchantUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.GetMerchantByIdUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.UpdateMerchantUseCase;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.CreateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.request.merchant.UpdateMerchantRequest;
import com.mitocode.qrpayment.infraestructure.in.web.dto.response.MerchantResponse;
import com.mitocode.qrpayment.infraestructure.in.web.mapper.MerchantDtoMapper;
import com.mitocode.qrpayment.infraestructure.in.web.mapper.MerchantRequestMapper;


//este es un Bol que agrupra varios casos de uso y se inyecta en el controlador para su uso
@Service
public class MerchantService {

    private final CreateMerchantUseCase createMerchantUseCase;
    private final GetAllMerchantUseCase getAllMerchantUseCase;
    private final GetMerchantByIdUseCase getMerchantByIdUseCase;
    private final UpdateMerchantUseCase updateMerchantUseCase;
    private final DeleteMerchantUseCase deleteMerchantUseCase;

    public MerchantService(CreateMerchantUseCase createMerchantUseCase,
                           GetAllMerchantUseCase getAllMerchantUseCase,
                           GetMerchantByIdUseCase getMerchantByIdUseCase,
                           UpdateMerchantUseCase updateMerchantUseCase,
                           DeleteMerchantUseCase deleteMerchantUseCase) {
        this.createMerchantUseCase = createMerchantUseCase;
        this.getAllMerchantUseCase = getAllMerchantUseCase;
        this.getMerchantByIdUseCase = getMerchantByIdUseCase;	
        this.updateMerchantUseCase = updateMerchantUseCase;
        this.deleteMerchantUseCase = deleteMerchantUseCase;
    }

    public MerchantResponse createMerchant(CreateMerchantRequest request){
        CreateMerchantCommand command = MerchantRequestMapper.toCommand(request);
        MerchantDto dto = createMerchantUseCase.execute(command);
        return MerchantDtoMapper.toResponse(dto);
    }

    public MerchantResponse updateMerchant(UpdateMerchantRequest request){
        UpdateMerchantCommand command = MerchantRequestMapper.toCommand(request);
        MerchantDto dto = updateMerchantUseCase.execute(command);
        return MerchantDtoMapper.toResponse(dto);
    }

    public MerchantResponse deleteMerchant(String merchantId){
        MerchantDto dto = deleteMerchantUseCase.execute(merchantId);
        return MerchantDtoMapper.toResponse(dto);
    }

    public MerchantResponse getMerchant(String merchantId){
        MerchantDto dto = getMerchantByIdUseCase.execute(merchantId);
        return MerchantDtoMapper.toResponse(dto);
    }

    public List<MerchantResponse> getAllMerchants(){
    	//tengo 2 opciones como en el ejemplo del controller de mitocode usercontroller
        //List<MerchantDto> dtos = getAllMerchantUseCase.execute();
        //return MerchantDtoMapper.toResponseList(dtos);
        List<MerchantResponse> list = getAllMerchantUseCase.execute().stream().map(MerchantDtoMapper::toResponse).toList();
        return list;
    }
}