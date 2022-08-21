package com.mydomomain.silverpay.controller.site.V1.accountant;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.dto.site.panel.PaginationDto;
import com.mydomomain.silverpay.dto.site.panel.factor.FactorStatusDto;
import com.mydomomain.silverpay.model.ReturnMessage;
import com.mydomomain.silverpay.model.Wallet;
import com.mydomomain.silverpay.model.accountant.Factor;
import com.mydomomain.silverpay.repository.financialDBRepository.IFactorRepository;
import com.mydomomain.silverpay.repository.mainRepository.IWalletRepository;
import com.mydomomain.silverpay.service.userService.UserService;
import com.mydomomain.silverpay.service.userService.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FactorController {


    final IFactorRepository factorRepository;

    final IWalletRepository walletRepository;

    final WalletService walletService;


    final UserService userService;

    FactorController(IFactorRepository factorRepository, IWalletRepository walletRepository, UserService userService, WalletService walletService) {

        this.factorRepository = factorRepository;
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.walletService = walletService;
    }


    @GetMapping(Routes.Factor.get_factors)
    public ResponseEntity<Object> getEntries(PaginationDto paginationDto) {

        List<Factor> factors = factorRepository.findAll();//apply pagination and sorting


        return new ResponseEntity<>(factors, HttpStatus.OK);

    }

    @GetMapping(Routes.Factor.get_factor)
    public ResponseEntity<Object> getFactor(@PathVariable String factor_id) {


        Factor factorRepo = factorRepository.findById(factor_id).orElse(null);

        if (factorRepo != null) {

            return ResponseEntity.ok().body(factorRepo);

        } else {
            return ResponseEntity.badRequest().body("no factor found");
        }

    }

    @PutMapping(Routes.Factor.status_factor)
    public ResponseEntity<?> changeFactorStatus(
            FactorStatusDto statusDto,
            @PathVariable String factor_id) {


        Factor factorRepo = factorRepository.findById(factor_id).orElse(null);

        if (factorRepo != null) {

            if (statusDto.isStatus()) {

                if (factorRepo.isStatus()) {
                    return ResponseEntity.noContent().build();
                } else {

                    ReturnMessage result = walletService.increase(factorRepo.getEndPrice(), factorRepo.getWalletId(), true);

                    if (!result.isStatus()) {
                        return ResponseEntity.badRequest().body("error in update wallet");
                    }

                }

            } else {

                if (!factorRepo.isStatus()) {
                    return ResponseEntity.noContent().build();
                } else {
                    ReturnMessage result = walletService.decrease(factorRepo.getEndPrice()
                            , factorRepo.getWalletId(), true);
                    if (!result.isStatus()) {
                        return ResponseEntity.badRequest().body("error in update wallet");
                    }

                }

            }

            factorRepo.setStatus(statusDto.isStatus());
            factorRepository.save(factorRepo);
            return ResponseEntity.ok().body(factorRepo);

        } else {
            return ResponseEntity.badRequest().body("no factor found");
        }

    }


    @DeleteMapping(Routes.Factor.delete_factor)
    public ResponseEntity<?> deleteFactor(
            @PathVariable String factor_id) {


        Factor factorRepo = factorRepository.findById(factor_id).orElse(null);

        if (factorRepo != null) {
            factorRepository.delete(factorRepo);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.badRequest().body("no factor found");

        }


    }


}
