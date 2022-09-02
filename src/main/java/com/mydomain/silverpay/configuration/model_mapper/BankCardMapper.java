package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.dto.site.panel.upload.users.BankCardReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.BankCardUpdateDto;
import com.mydomain.silverpay.data.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomain.silverpay.data.model.mainDB.BankCard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BankCardMapper {

    BankCardMapper instance = Mappers.getMapper(BankCardMapper.class);

    BankCardReturnDto bankCardMapper(BankCard bankCard);

    BankCard bankCardMapper(BankCardUpdateDto bankCard);

    List<BankCardUserDetailDto> bankCardMapper(List<BankCard> bankCards);

    List<BankCardReturnDto> ToReturnDtoList(List<BankCard> bankCards);

}
