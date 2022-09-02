package com.mydomain.silverpay.controller.site.V1.accountant;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.GateMapper;
import com.mydomain.silverpay.configuration.model_mapper.WalletMapper;
import com.mydomain.silverpay.data.dto.common.FactorPaginationDto;
import com.mydomain.silverpay.data.dto.site.panel.accountant.EditFactorDto;
import com.mydomain.silverpay.data.dto.site.panel.accountant.FactorReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.factor.FactorStatusDto;
import com.mydomain.silverpay.data.dto.site.panel.gate.GateWalletsReturnDto;
import com.mydomain.silverpay.data.model.mainDB.Gate;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.MainDB.IGateRepository;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import com.mydomain.silverpay.data.dto.site.panel.PaginationDto;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import com.mydomain.silverpay.repository.FinancialDB.IFactorRepository;
import com.mydomain.silverpay.service.userService.UserService;
import com.mydomain.silverpay.service.userService.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController("accountant_factor")
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
    public ResponseEntity<Object> getFactors(FactorPaginationDto paginationDto) {

        List<Factor> factors = factorRepository.findAll();//apply pagination and sorting


        return new ResponseEntity<>(factors, HttpStatus.OK);

    }

    @GetMapping(Routes.Factor.get_wallet_factors)
    public ResponseEntity<Object> getWalletFactors(@PathVariable String wallet_id, PaginationDto paginationDto) {

        List<Factor> factors = factorRepository.findAll()
                .stream().filter(factor -> factor.getWalletId().equals(wallet_id)).collect(Collectors.toList());//apply pagination and sorting


        return new ResponseEntity<>(factors, HttpStatus.OK);

    }

    @GetMapping(Routes.Factor.get_gate_factors)
    public ResponseEntity<Object> getGatesFactors(@PathVariable String gate_id, PaginationDto paginationDto) {

        List<Factor> factors = factorRepository.findAll()
                .stream().filter(factor -> factor.getWalletId().equals(gate_id)).collect(Collectors.toList());//apply pagination and sorting


        return new ResponseEntity<>(factors, HttpStatus.OK);

    }


    @GetMapping(Routes.Factor.get_gate)
    public ResponseEntity<?> getGate(@PathVariable String user_id,
                                     @PathVariable String id,
                                     Principal principal,
                                     HttpServletRequest request) {


        Gate gateFromRepo = gateRepository.findById(id).orElse(null);

        if (gateFromRepo != null) {

            if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
                ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
                return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
            }

            List<Wallet> wallets = walletRepository.findAll().stream().filter(w -> w.getUser().getId().equals(user_id))
                    .sorted(Comparator.comparing(Wallet::isMain))
                    .sorted(Comparator.comparing(Wallet::isSms))
                    .collect(Collectors.toList());

            GateWalletsReturnDto returnDto = new GateWalletsReturnDto();
            returnDto.setGate(GateMapper.instance.gateToDto(gateFromRepo));
            returnDto.setWalletList(WalletMapper.instance.walletMapper(wallets));

            return new ResponseEntity<>(returnDto, HttpStatus.OK);


        } else {
            return new ResponseEntity<>("no gate found", HttpStatus.NOT_FOUND);

        }


    }

    @GetMapping(Routes.Factor.get_factor)
    public ResponseEntity<Object> getFactor(@PathVariable String factor_id) {


        Factor factorRepo = factorRepository.findById(factor_id).orElse(null);

        if (factorRepo != null) {

            User userRepo = userRepository.findById(factorRepo.getUserId()).orElse(null);

            factorRepo.setUserName(userRepo.getName());

            factorRepository.save(factorRepo);


            Gate gateRepo = gateRepository.findById(factorRepo.getGateId()).orElse(null);

            Wallet walletRepo = walletRepository.findById(factorRepo.getWalletId()).orElse(null);

            FactorReturnDto factorReturnDto = new FactorReturnDto();
            factorReturnDto.setFactor(factorRepo);
            factorReturnDto.setGate(GateMapper.instance.gateToDto(gateRepo));
            factorReturnDto.setWallet(WalletMapper.instance.walletMapper(walletRepo));


            return ResponseEntity.ok().body(factorReturnDto);

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

                    Wallet walletReturn=walletRepository.findById(factorRepo.getWalletId()).orElse(null);//check in s38 part 3 26:08
                    return ResponseEntity.ok(walletReturn);
                } else {

                    ReturnMessage result = walletService.increase(factorRepo.getEndPrice(), factorRepo.getWalletId(), true);

                    if (!result.isStatus()) {
                        return ResponseEntity.badRequest().body("error in update wallet");
                    }

                }

            } else {

                if (!factorRepo.isStatus()) {
                    Wallet walletReturn=walletRepository.findById(factorRepo.getWalletId()).orElse(null);//check in s38 part 3 26:08
                    return ResponseEntity.ok(walletReturn);
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

            Wallet walletReturn=walletRepository.findById(factorRepo.getWalletId()).orElse(null);//check in s38 part 3 26:08
            return ResponseEntity.ok(walletReturn);

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

    @PatchMapping(Routes.Factor.edit_factor)
    public ResponseEntity<?> editFactor(
            @PathVariable String factor_id, EditFactorDto editDto) {


        Factor factorRepo = factorRepository.findById(factor_id).orElse(null);

        if (factorRepo != null) {
            factorRepo.setRefBank(editDto.getRefBank());
            factorRepository.save(factorRepo);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.badRequest().body("no factor found");

        }


    }


}
