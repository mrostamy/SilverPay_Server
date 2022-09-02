package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.model.mainDB.Wallet;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountantMapper {

    AccountantMapper instance = Mappers.getMapper(AccountantMapper.class);

    @Mapping(source = "user.wallets", target = "inventorySum", qualifiedByName = "inventory")
    @Mapping(source = "user.wallets", target = "EnterMoneySum", qualifiedByName = "enterMoney")
    @Mapping(source = "user.wallets", target = "exitMoneySum", qualifiedByName = "exitMoney")
    @Mapping(source = "user.wallets", target = "OnExitMoneySum", qualifiedByName = "onExitMoney")
//    AccountantUserDto userToAccountantDto(User user);


    @Name("inventory")
    default int inventorySum(List<Wallet> wallets) {

       int sum = 0;
        for (Wallet wallet : wallets) {
            sum += wallet.getInventory();
        }

        return sum;
    }

    @Name("enterMoney")
    default int enterMoneySum(List<Wallet> wallets) {

        int sum = 0;
        for (Wallet wallet : wallets) {
            sum += wallet.getEnterMoney();
        }
        return sum;
    }


    @Name("exitMoney")
    default int exitMoney(List<Wallet> wallets) {

        int sum = 0;
        for (Wallet wallet : wallets) {
            sum += wallet.getExitMoney();
        }
        return sum;
    }

    @Name("onExitMoney")
    default int onExitMoneySum(List<Wallet> wallets) {

        int sum = 0;
        for (Wallet wallet : wallets) {
            sum += wallet.getOnExitMoney();
        }
        return sum;
    }


}
