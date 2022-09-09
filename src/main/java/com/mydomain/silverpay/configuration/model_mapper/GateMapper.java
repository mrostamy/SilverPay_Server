package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.dto.app.panel.gate.GateCreateDto;
import com.mydomain.silverpay.data.dto.app.panel.gate.GateReturnDto;
import com.mydomain.silverpay.data.model.mainDB.Gate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GateMapper {

    GateMapper instance = Mappers.getMapper(GateMapper.class);

    GateReturnDto gateToDto(Gate gate);

    Gate dtoToGate(GateCreateDto gate);

    List<GateReturnDto> gateToDto(List<Gate> gateList);

    List<GateReturnDto> walletReturn(List<Gate> gates);

}
