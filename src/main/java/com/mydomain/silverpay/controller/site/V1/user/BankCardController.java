package com.mydomain.silverpay.controller.site.V1.user;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.BankCardUpdateDto;
import com.mydomain.silverpay.repository.FinancialDB.IEntryRepository;
import com.mydomain.silverpay.repository.MainDB.IBankCardRepository;
import com.mydomain.silverpay.configuration.model_mapper.BankCardMapper;
import com.mydomain.silverpay.data.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.BankCardReturnDto;
import com.mydomain.silverpay.data.model.mainDB.BankCard;
import com.mydomain.silverpay.data.model.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class BankCardController {


    Logger logger = LoggerFactory.getLogger(BankCardController.class);

    final IBankCardRepository bankCardRepository;

    final IEntryRepository entryRepository;


    BankCardController(IBankCardRepository bankCardRepository, IEntryRepository entryRepository) {

        this.bankCardRepository = bankCardRepository;
        this.entryRepository = entryRepository;
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

    @GetMapping(Routes.BankCard.bankCards)
    public ResponseEntity<?> getBankCards(@PathVariable String user_id,
                                          Principal principal,
                                          HttpServletRequest request) {


        List<BankCard> bankCards = bankCardRepository.findAll()
                .stream().filter(b -> Objects.equals(b.getUser().getId(), user_id)).collect(Collectors.toList());

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }


        List<BankCardUserDetailDto> returnBankCards = BankCardMapper.instance.bankCardMapper(bankCards);


        return new ResponseEntity<>(bankCards, HttpStatus.OK);

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

            boolean exist = entryRepository.findAll().stream().anyMatch(entry -> entry.getWalletId().equals(id));

            if (exist)
                return ResponseEntity.badRequest().body("this card have registered entry");


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


    @PostMapping(Routes.BankCard.insert)
    public ResponseEntity<?> changeUserBankCard(
            @RequestBody BankCardUpdateDto updateDto
            , HttpServletRequest request,
            @PathVariable String user_id) throws URISyntaxException {


        boolean exist = bankCardRepository.findAll().
                stream().anyMatch(b -> Objects.equals(b.getCardNumber(), updateDto.getCardNumber()));

        if (exist)
            return new ResponseEntity<>("the bank card registered before", HttpStatus.BAD_REQUEST);

        if (bankCardRepository.countByUser_id(user_id) > 10) {

            return new ResponseEntity<>("total bank card(10) reached", HttpStatus.BAD_REQUEST);

        }

        BankCard bankCard = new BankCard();
        bankCard.setId(user_id);
        bankCard.setApprove(false);

        bankCard = BankCardMapper.instance.bankCardMapper(updateDto);

        bankCardRepository.save(bankCard);


        return ResponseEntity.created(new URI("?id=" + user_id)).body(bankCard);

    }


}
