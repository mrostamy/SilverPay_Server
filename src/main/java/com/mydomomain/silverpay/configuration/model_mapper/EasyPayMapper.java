package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomomain.silverpay.dto.site.panel.users.BankCardReturnDto;
import com.mydomomain.silverpay.dto.site.panel.users.BankCardUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.users.EasyPayCreateUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.users.EasyPayReturnDto;
import com.mydomomain.silverpay.model.BankCard;
import com.mydomomain.silverpay.model.EasyPay;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EasyPayMapper {

    EasyPayMapper instance = Mappers.getMapper(EasyPayMapper.class);

    EasyPayReturnDto easyPayToReturnDtoMapper(EasyPay bankCard);

    EasyPay easyPayMapper(EasyPayCreateUpdateDto easyPay);

    List<EasyPayReturnDto> easyPaysToDtoMapper(List<EasyPay> easyPays);



}
