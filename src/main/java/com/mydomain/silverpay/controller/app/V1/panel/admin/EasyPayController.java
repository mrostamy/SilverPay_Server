package com.mydomain.silverpay.controller.app.V1.panel.admin;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.EasyPayMapper;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.EasyPayReturnDto;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.data.model.mainDB.EasyPay;
import com.mydomain.silverpay.repository.MainDB.IEasyPayRepository;
import com.mydomain.silverpay.repository.MainDB.IGateRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController("admin_EasyPayController")
public class EasyPayController {


    Logger logger = LoggerFactory.getLogger(com.mydomain.silverpay.controller.app.V1.panel.user.EasyPayController.class);

    final IEasyPayRepository easyPayRepository;


    @Autowired
    IWalletRepository walletRepository;


    @Autowired
    IGateRepository gateRepository;


    EasyPayController(IEasyPayRepository easyPayRepository) {

        this.easyPayRepository = easyPayRepository;
    }


    @GetMapping(Routes.EasyPay.get_easyPays)
    public ResponseEntity<?> getEasyPays(@PathVariable String user_id,
                                         Principal principal,
                                         HttpServletRequest request) {


        List<EasyPay> easyPays = easyPayRepository.findAll()
                .stream().filter(b -> Objects.equals(b.getUser().getId(), user_id))
                .sorted(Comparator.comparing(EasyPay::getModifiedAt))
                .collect(Collectors.toList());

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }


        List<EasyPayReturnDto> returnEasyPays = EasyPayMapper.instance.easyPaysToDtoMapper(easyPays);


        return new ResponseEntity<>(returnEasyPays, HttpStatus.OK);

    }


    @GetMapping(Routes.EasyPay.get_easyPay)
    public ResponseEntity<?> getEasyPay(@PathVariable String user_id,
                                        @PathVariable String id,
                                        Principal principal,
                                        HttpServletRequest request) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        } else {

            EasyPay easyPay = easyPayRepository.findById(id).orElse(null);

            if (easyPay != null) {
                EasyPayReturnDto ReturnDto = EasyPayMapper.instance.easyPayToReturnDtoMapper(easyPay);

                return new ResponseEntity<>(ReturnDto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no bank card found", HttpStatus.NOT_FOUND);

            }
        }


    }


    @DeleteMapping(Routes.EasyPay.delete_easyPay)
    public ResponseEntity<?> changeUserEasyPay(
            @PathVariable String id
            , HttpServletRequest request
            , Authentication principal) throws URISyntaxException {


        EasyPay easyPay = easyPayRepository.findById(id).orElse(null);

        if (easyPay != null) {

            if (!principal.getName().equals(easyPay.getUser().getId())) {

                ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorized Access Detected", "error");
                return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);

            } else {
                easyPayRepository.delete(easyPay);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }

        } else {
            return new ResponseEntity<>("no easyPay found", HttpStatus.NOT_FOUND);

        }
    }



}
