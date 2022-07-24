package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.controller.site.V1.user.BankCardUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.users.BankCardReturnDto;
import com.mydomomain.silverpay.model.BankCard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankCardMapper {

    BankCardMapper instance = Mappers.getMapper(BankCardMapper.class);


    BankCardReturnDto bankCardMapper(BankCard bankCard);

    BankCard bankCardMapper(BankCardUpdateDto bankCard);
}
