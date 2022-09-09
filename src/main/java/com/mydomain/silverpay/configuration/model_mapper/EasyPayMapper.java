package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.dto.app.panel.upload.users.EasyPayCreateUpdateDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.EasyPayReturnDto;
import com.mydomain.silverpay.data.model.mainDB.EasyPay;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EasyPayMapper {

    EasyPayMapper instance = Mappers.getMapper(EasyPayMapper.class);

    EasyPayReturnDto easyPayToReturnDtoMapper(EasyPay easyPay);

    EasyPay easyPayMapper(EasyPayCreateUpdateDto easyPay);

    List<EasyPayReturnDto> easyPaysToDtoMapper(List<EasyPay> easyPays);



}
