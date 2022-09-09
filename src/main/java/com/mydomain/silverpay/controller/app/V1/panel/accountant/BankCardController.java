package com.mydomain.silverpay.controller.app.V1.panel.accountant;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.BankCardMapper;
import com.mydomain.silverpay.data.dto.app.panel.bank_card.BankCardUserDetailDto;
import com.mydomain.silverpay.data.model.mainDB.BankCard;
import com.mydomain.silverpay.repository.MainDB.IBankCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController("accountant_bankcard")
public class BankCardController {

    @Autowired
    IBankCardRepository bankCardRepository;

    @GetMapping(Routes.Accountant.getBankCards)
    public ResponseEntity<?> getBankCards(
            Principal principal,
            HttpServletRequest request) {

        List<BankCard> bankCards = bankCardRepository.findAll();

//        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
//            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
//            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//        }
//

        List<BankCardUserDetailDto> returnDtos = BankCardMapper.instance.bankCardMapper(bankCards);


        return new ResponseEntity<>(bankCards, HttpStatus.OK);

        //apply pagination and sorting

    }


}
