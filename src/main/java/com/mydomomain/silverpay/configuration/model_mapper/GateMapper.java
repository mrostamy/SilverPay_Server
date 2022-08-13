package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.document.DocumentCreateDto;
import com.mydomomain.silverpay.dto.site.panel.document.DocumentReturnDto;
import com.mydomomain.silverpay.dto.site.panel.gate.GateCreateDto;
import com.mydomomain.silverpay.dto.site.panel.gate.GateReturnDto;
import com.mydomomain.silverpay.dto.site.panel.users.WalletReturnDto;
import com.mydomomain.silverpay.model.Document;
import com.mydomomain.silverpay.model.Gate;
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
