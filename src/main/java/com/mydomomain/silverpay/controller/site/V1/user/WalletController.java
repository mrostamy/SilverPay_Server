package com.mydomomain.silverpay.controller.site.V1.user;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.WalletMapper;
import com.mydomomain.silverpay.model.ReturnMessage;
import com.mydomomain.silverpay.model.Wallet;
import com.mydomomain.silverpay.repository.main.IWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class WalletController {


    Logger logger = LoggerFactory.getLogger(WalletController.class);

    final IWalletRepository walletRepository;


    WalletController(IWalletRepository walletRepository) {

        this.walletRepository = walletRepository;
    }


    @GetMapping(Routes.Wallet.get_wallet)
    public ResponseEntity<?> getWallet(@PathVariable String user_id,
                                       @PathVariable String id,
                                       Principal principal,
                                       HttpServletRequest request) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        } else {

            Wallet wallet = walletRepository.findById(id).orElse(null);

            if (wallet != null) {
                WalletReturnDto cardReturnDto = WalletMapper.instance.walletMapper(wallet);

                return new ResponseEntity<>(cardReturnDto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no wallet found", HttpStatus.NOT_FOUND);

            }
        }


    }

    @GetMapping(Routes.Wallet.get_wallets)
    public ResponseEntity<?> getWallets(@PathVariable String user_id,
                                        Principal principal,
                                        HttpServletRequest request) {


        List<Wallet> wallets = walletRepository.findAll()
                .stream().filter(b -> Objects.equals(b.getUser().getId(), user_id))
                .sorted(Comparator.comparing(Wallet::isMain))
                .sorted(Comparator.comparing(Wallet::isSms))
                .collect(Collectors.toList());

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }


        List<WalletReturnDto> returnWallets = WalletMapper.instance.walletMapper(wallets);


        return new ResponseEntity<>(wallets, HttpStatus.OK);

    }


    @PostMapping(Routes.Wallet.insert_wallet)
    public ResponseEntity<?> changeUserWallet(
            @RequestBody WalletCreateDto createDto
            , HttpServletRequest request,
            @PathVariable String user_id) throws URISyntaxException {


        boolean exist = walletRepository.findAll().
                stream()
                .anyMatch(b ->
                        Objects.equals(b.getName(), createDto.getName()) && Objects.equals(b.getUser().getId(), user_id));

        if (exist)
            return new ResponseEntity<>("the wallet registered before", HttpStatus.BAD_REQUEST);

        if (walletRepository.countByUser_id(user_id) > 10) {

            return new ResponseEntity<>("total wallet(10) reached", HttpStatus.BAD_REQUEST);

        }

        long code = walletRepository.findAllProjectBy().stream().findFirst().orElse(null).getCode()+1;

        Wallet wallet = new Wallet();
        wallet.setId(user_id);
        wallet.setCode(code);

        wallet = WalletMapper.instance.walletMapper(createDto);

        walletRepository.save(wallet);


        return ResponseEntity.created(new URI("?id=" + user_id)).body(wallet);

    }


}
