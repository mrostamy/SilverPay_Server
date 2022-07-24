package com.mydomomain.silverpay.controller.site.V1.user;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.BankCardMapper;
import com.mydomomain.silverpay.dto.site.panel.users.BankCardReturnDto;
import com.mydomomain.silverpay.model.BankCard;
import com.mydomomain.silverpay.model.ReturnMessage;
import com.mydomomain.silverpay.repository.main.IBankCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.security.Principal;

@RestController
public class BankCardController {


    Logger logger = LoggerFactory.getLogger(BankCardController.class);

    final IBankCardRepository bankCardRepository;


    BankCardController(IBankCardRepository bankCardRepository) {

        this.bankCardRepository = bankCardRepository;
    }


    @GetMapping(Routes.BankCard.bankCard)
    public ResponseEntity<?> getBankCard(@PathVariable String user_id,
                                         @PathVariable String id,
                                         Principal principal,
                                         HttpServletRequest request) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        } else {

            BankCard bankCard = bankCardRepository.findById(id).orElse(null);

            if (bankCard != null) {
                BankCardReturnDto cardReturnDto = BankCardMapper.instance.bankCardMapper(bankCard);

                return new ResponseEntity<>(cardReturnDto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no bank card found", HttpStatus.NOT_FOUND);

            }
        }


    }

    @PutMapping(Routes.BankCard.update)
    public ResponseEntity<?> changeUserBankCard(
            @PathVariable String id,
            BankCardUpdateDto updateDto
            , HttpServletRequest request
            , Authentication principal) throws URISyntaxException {


        BankCard bankCard = bankCardRepository.findById(id).orElse(null);

        if (bankCard != null) {

            if (!principal.getName().equals(bankCard.getUser().getId())) {

                ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorized Access Detected", "error");
                return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);

            } else {

                BankCard updateBankCard = BankCardMapper.instance.bankCardMapper(updateDto);

                bankCardRepository.save(updateBankCard);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }

        } else {
            return new ResponseEntity<>("no bank card found", HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping(Routes.BankCard.delete)
    public ResponseEntity<?> changeUserBankCard(
            @PathVariable String id
            , HttpServletRequest request
            , Authentication principal) throws URISyntaxException {


        BankCard bankCard = bankCardRepository.findById(id).orElse(null);

        if (bankCard != null) {

            if (!principal.getName().equals(bankCard.getUser().getId())) {

                ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorized Access Detected", "error");
                return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);

            } else {
                bankCardRepository.delete(bankCard);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }

        } else {
            return new ResponseEntity<>("no bank card found", HttpStatus.NOT_FOUND);

        }
    }
}
