package com.mydomomain.silverpay.controller.site.V1.user;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.EasyPayMapper;
import com.mydomomain.silverpay.dto.site.panel.users.EasyPayCreateUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.users.EasyPayReturnDto;
import com.mydomomain.silverpay.model.*;
import com.mydomomain.silverpay.repository.mainRepository.IEasyPayRepository;
import com.mydomomain.silverpay.repository.mainRepository.IGateRepository;
import com.mydomomain.silverpay.repository.mainRepository.IWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class EasyPayController {


    Logger logger = LoggerFactory.getLogger(EasyPayController.class);

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

    @GetMapping(Routes.EasyPay.get_easyPayGatesWallets)
    public ResponseEntity<?> getEasyPayGatesWallets(@PathVariable String user_id,
                                                    @PathVariable String id,
                                                    Principal principal,
                                                    HttpServletRequest request) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        } else {

            EasyPay easyPay = easyPayRepository.findById(id).orElse(null);

            if (easyPay != null) {

                EasyPayReturnDto easyPayReturnDto = EasyPayMapper.instance.easyPayToReturnDtoMapper(easyPay);


                List<Gate> gatesRepo = gateRepository.findAll().stream()
                        .filter(g ->
                                g.getWallet().getUser().getId().equals(user_id)
                        )
                        .sorted(Comparator.comparing(Gate::isActive)).collect(Collectors.toList());

                List<Wallet> walletsRepo = walletRepository.findAll().stream()
                        .filter(g ->
                                g.getUser().getId().equals(user_id)
                        )
                        .sorted(Comparator.comparing(Wallet::isMain))
                        .sorted(Comparator.comparing(Wallet::isSms))
                        .collect(Collectors.toList());

                EasyPayGatesWalletsReturnDto result = new EasyPayGatesWalletsReturnDto();
                result.setEasyPay(easyPayReturnDto);
                result.setGates(gatesRepo);
                result.setWallets(walletsRepo);


                return new ResponseEntity<>(result, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no bank card found", HttpStatus.NOT_FOUND);

            }
        }


    }


    @PostMapping(Routes.EasyPay.insert_easyPays)
    public ResponseEntity<?> changeUserEasyPay(
            @RequestBody EasyPayCreateUpdateDto createDto
            , HttpServletRequest request,
            @PathVariable String user_id) throws URISyntaxException {


        List<EasyPay> easyPaysRepo = easyPayRepository.findAll().
                stream().filter(e -> Objects.equals(e.getName(), createDto.getName()) && e.getUser().getId().equals(user_id))
                .collect(Collectors.toList());

        if (easyPaysRepo != null)
            return new ResponseEntity<>("the easyPay registered before", HttpStatus.BAD_REQUEST);


        EasyPay easyPay = new EasyPay();
        easyPay.getUser().setId(user_id);

        if (!createDto.isCountLimit()) {
            createDto.setCountLimit(0);
        }

        easyPay = EasyPayMapper.instance.easyPayMapper(createDto);

        easyPayRepository.save(easyPay);


        return ResponseEntity.created(new URI("?id=" + user_id)).body(easyPay);

    }

    @PutMapping(Routes.EasyPay.update_easyPay)
    public ResponseEntity<?> changeUserEasyPay(
            @PathVariable String id,
            EasyPayCreateUpdateDto updateDto
            , HttpServletRequest request
            , Authentication principal
            , @PathVariable String user_id) throws URISyntaxException {

        List<EasyPay> easyPaysRepo = easyPayRepository.findAll().
                stream().
                filter(e -> Objects.equals(e.getName(), updateDto.getName()) && e.getUser().getId().equals(user_id)
                        && e.getId().equals(id)
                )
                .collect(Collectors.toList());


        if (easyPaysRepo == null) {
            EasyPay easyPay = easyPayRepository.findById(id).orElse(null);

            if (easyPay != null) {

                if (!principal.getName().equals(easyPay.getUser().getId())) {

                    ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorized Access Detected", "error");
                    return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);

                }
                EasyPay updateEasyPay = EasyPayMapper.instance.easyPayMapper(updateDto);
                updateEasyPay.setModifiedAt(LocalDateTime.now());
                if (!updateDto.isCountLimit())
                    updateDto.setCountLimit(0);

                easyPayRepository.save(updateEasyPay);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);


            }

        } else {

            return new ResponseEntity<>("no easyPay found", HttpStatus.NOT_FOUND);

        }

        return null;
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
