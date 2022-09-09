package com.mydomain.silverpay.configuration.model_mapper;


import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.WalletCreateDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.WalletReturnDto;
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
