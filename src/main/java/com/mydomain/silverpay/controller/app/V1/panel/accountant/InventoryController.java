package com.mydomain.silverpay.controller.app.V1.panel.accountant;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.BankCardMapper;
import com.mydomain.silverpay.configuration.model_mapper.UserMapper;
import com.mydomain.silverpay.configuration.model_mapper.WalletMapper;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.BankCardReturnDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.UserDetailDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.WalletReturnDto;
import com.mydomain.silverpay.data.model.mainDB.BankCard;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.MainDB.IBankCardRepository;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InventoryController {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IWalletRepository walletRepository;

    @Autowired
    IBankCardRepository bankCardRepository;

    @GetMapping(Routes.Accountant.inventories)
    public ResponseEntity<Object> getInventories() {

        List<User> users = userRepository.findAll();

        List<UserDetailDto> userDtos = new ArrayList<>();

        users.forEach(user -> userDtos.add(UserMapper.instance.detailDto(user)));

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


    @GetMapping(Routes.Accountant.inventoryWallets)
    public ResponseEntity<Object> getInventoryWallets(@PathVariable String userId) {


        List<Wallet> walletsRepo = walletRepository.findAll().stream()
                .filter(w -> w.getUser().getId().equals(userId))
                .sorted(Comparator.comparing(Wallet::isMain))
                .sorted(Comparator.comparing(Wallet::isSms))
                .collect(Collectors.toList());

        List<WalletReturnDto> wallets = WalletMapper.instance.walletMapper(walletsRepo);

        return new ResponseEntity<>(wallets, HttpStatus.OK);

    }

    @GetMapping(Routes.Accountant.inventoryBankCards)
    public ResponseEntity<Object> getInventoryBankCards(@PathVariable String userId) {

        List<BankCard> bankCardsRepo = bankCardRepository.findAll().stream()
                .filter(w -> w.getUser().getId().equals(userId))
                .sorted(Comparator.comparing(BankCard::isApprove))
                .collect(Collectors.toList());

        List<BankCardReturnDto> bankCards = BankCardMapper.instance.ToReturnDtoList(bankCardsRepo);

        return new ResponseEntity<>(bankCards, HttpStatus.OK);

    }




}
