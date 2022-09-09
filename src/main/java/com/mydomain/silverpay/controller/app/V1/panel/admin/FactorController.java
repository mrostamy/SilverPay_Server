package com.mydomain.silverpay.controller.app.V1.panel.admin;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.data.dto.common.FactorPaginationDto;
import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import com.mydomain.silverpay.repository.FinancialDB.IFactorRepository;
import com.mydomain.silverpay.repository.MainDB.IGateRepository;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import com.mydomain.silverpay.service.userService.UserService;
import com.mydomain.silverpay.service.userService.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("admin_factor")
public class FactorController {


    final IFactorRepository factorRepository;

    final IWalletRepository walletRepository;

    final IUserRepository userRepository;

    final WalletService walletService;

    final IGateRepository gateRepository;


    final UserService userService;

    FactorController(IFactorRepository factorRepository, IWalletRepository walletRepository, UserService userService, WalletService walletService, IUserRepository userRepository, IGateRepository gateRepository) {

        this.factorRepository = factorRepository;
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.walletService = walletService;
        this.userRepository = userRepository;
        this.gateRepository = gateRepository;
    }


    @GetMapping(Routes.Factor.get_factors)
    public ResponseEntity<Object> getUserFactors(String user_id,FactorPaginationDto paginationDto) {

        List<Factor> factors = factorRepository.findAll();//apply pagination and sorting


        return new ResponseEntity<>(factors, HttpStatus.OK);

    }

}
