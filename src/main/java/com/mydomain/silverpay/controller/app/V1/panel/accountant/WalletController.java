package com.mydomain.silverpay.controller.app.V1.panel.accountant;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.WalletMapper;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.WalletBlockDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.WalletReturnDto;
import com.mydomain.silverpay.data.model.mainDB.Gate;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.MainDB.IGateRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController("accountant_wallet")
public class WalletController {


    final IWalletRepository walletRepository;

    final IGateRepository gateRepository;

    public WalletController(IWalletRepository walletRepository, IGateRepository gateRepository) {
        this.walletRepository = walletRepository;
        this.gateRepository = gateRepository;
    }

    @GetMapping(Routes.Accountant.getWalletGates)
    public ResponseEntity<?> getGateWallets(
            Principal principal,
            HttpServletRequest request, @PathVariable String wallet_id) {


        List<Gate> gates = gateRepository.findAll().stream().filter(g -> g.getWallet().getId().equals(wallet_id))
                .collect(Collectors.toList());

//        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
//            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
//            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//        }
//

//        List<WalletReturnDto> returnWallets = WalletMapper.instance.walletMapper(wallets);


        return new ResponseEntity<>(gates, HttpStatus.OK);

        //apply pagination and sorting

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


    @GetMapping(Routes.Accountant.inventoryBlockWallet)
    public ResponseEntity<Object> blockWallet(@PathVariable String wallet_id, WalletBlockDto flag) {

        Wallet walletRepo = walletRepository.findById(wallet_id).orElse(null);

        if (walletRepo != null) {
            walletRepo.setBlock(flag.isFlag());

            walletRepository.save(walletRepo);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }




}
