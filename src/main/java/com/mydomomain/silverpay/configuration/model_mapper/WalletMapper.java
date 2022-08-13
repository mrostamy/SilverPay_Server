package com.mydomomain.silverpay.configuration.model_mapper;


import com.mydomomain.silverpay.dto.site.panel.users.WalletCreateDto;
import com.mydomomain.silverpay.dto.site.panel.users.WalletReturnDto;
import com.mydomomain.silverpay.model.Gate;
import com.mydomomain.silverpay.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WalletMapper {

    WalletMapper instance = Mappers.getMapper(WalletMapper.class);

    WalletReturnDto walletMapper(Wallet wallet);

    List<WalletReturnDto> walletMapper(List<Wallet> wallet);

    Wallet walletMapper(WalletCreateDto createDto);

    List<WalletReturnDto> walletReturn(List<Wallet> gates);

}
