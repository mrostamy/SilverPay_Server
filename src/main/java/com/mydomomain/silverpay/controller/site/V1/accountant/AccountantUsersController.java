package com.mydomomain.silverpay.controller.site.V1.accountant;


import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.BankCardMapper;
import com.mydomomain.silverpay.configuration.model_mapper.UserMapper;
import com.mydomomain.silverpay.configuration.model_mapper.WalletMapper;
import com.mydomomain.silverpay.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomomain.silverpay.dto.site.panel.users.*;
import com.mydomomain.silverpay.model.BankCard;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.model.Wallet;
import com.mydomomain.silverpay.repository.mainRepository.IBankCardRepository;
import com.mydomomain.silverpay.repository.mainRepository.IUserRepository;
import com.mydomomain.silverpay.repository.mainRepository.IWalletRepository;
import com.mydomomain.silverpay.service.userService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountantUsersController {

    final IUserRepository userRepository;

    final IWalletRepository walletRepository;

    final IBankCardRepository bankCardRepository;

    final UserService userService;

    AccountantUsersController(IUserRepository userRepository, IWalletRepository walletRepository, IBankCardRepository bankCardRepository, UserService userService) {

        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.bankCardRepository = bankCardRepository;
        this.userService = userService;
    }


    @GetMapping(Routes.Accountant.inventories)
    public ResponseEntity<Object> getInventories() {

        List<User> users = userRepository.findAll();

        List<UserDetailDto> userDtos = new ArrayList<>();

        users.forEach(user -> userDtos.add(UserMapper.instance.userReturnDto(user)));

        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @GetMapping(Routes.Accountant.inventoryWallets)
    public ResponseEntity<Object> getInventories(@PathVariable String userId) {


        List<Wallet> walletsRepo = walletRepository.findAll().stream()
                .filter(w -> w.getUser().getId().equals(userId))
                .sorted(Comparator.comparing(Wallet::isMain))
                .sorted(Comparator.comparing(Wallet::isSms))
                .collect(Collectors.toList());

        List<WalletReturnDto> wallets= WalletMapper.instance.walletMapper(walletsRepo);

        return new ResponseEntity<>(wallets, HttpStatus.OK);

    }

    @GetMapping(Routes.Accountant.inventoryBankCards)
    public ResponseEntity<Object> getInventoryBankCards( @PathVariable String userId) {

        List<BankCard> bankCardsRepo = bankCardRepository.findAll().stream()
                .filter(w -> w.getUser().getId().equals(userId))
                .sorted(Comparator.comparing(BankCard::isApprove))
                .collect(Collectors.toList());

        List<BankCardReturnDto> bankCards= BankCardMapper.instance.ToReturnDtoList(bankCardsRepo);

        return new ResponseEntity<>(bankCards, HttpStatus.OK);

    }

    @GetMapping(Routes.Accountant.inventoryBlockWallet)
    public ResponseEntity<Object> blockInventoryWallet(String walletId, @PathVariable WalletBlockDto flag) {

        Wallet walletRepo = walletRepository.findById(walletId).orElse(null);

        if (walletRepo != null) {
            walletRepo.setBlock(flag.isFlag());

            walletRepository.save(walletRepo);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping(Routes.Accountant.inventoryBlockWallet)
    public ResponseEntity<Object> blockInventoryWallet(String bankCardId, @PathVariable BankCardApproveDto flag) {

        BankCard bankCardRepo = bankCardRepository.findById(bankCardId).orElse(null);

        if (bankCardRepo != null) {
            bankCardRepo.setApprove(flag.isApprove());

            bankCardRepository.save(bankCardRepo);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping(Routes.Accountant.getWallets)
    public ResponseEntity<?> getWallets(
                                        Principal principal,
                                        HttpServletRequest request) {


        List<Wallet> wallets = walletRepository.findAll();

//        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
//            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
//            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//        }
//

        List<WalletReturnDto> returnWallets = WalletMapper.instance.walletMapper(wallets);


        return new ResponseEntity<>(returnWallets, HttpStatus.OK);

        //apply pagination and sorting

    }

    @GetMapping(Routes.Accountant.getBankCards)
    public ResponseEntity<?> getBankCards(
                                          Principal principal,
                                          HttpServletRequest request) {


        List<BankCard> bankCards = bankCardRepository.findAll();

//        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
//            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
//            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//        }


        List<BankCardUserDetailDto> returnBankCards = BankCardMapper.instance.bankCardMapper(bankCards);


        return new ResponseEntity<>(bankCards, HttpStatus.OK);

        //apply pagination and sorting

    }



}
