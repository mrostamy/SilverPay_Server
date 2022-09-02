package com.mydomain.silverpay.controller.site.V1.user;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.GateMapper;
import com.mydomain.silverpay.configuration.model_mapper.WalletMapper;
import com.mydomain.silverpay.data.dto.site.panel.gate.GateCreateDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.GatesWalletsReturnDto;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import com.mydomain.silverpay.service.userService.WalletService;
import com.mydomain.silverpay.service.userService.upload.UploadService;
import com.mydomain.silverpay.data.dto.site.panel.gate.ActiveDirectGateDto;
import com.mydomain.silverpay.data.dto.site.panel.gate.GateReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.gate.GateWalletsReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.FileUploadedDto;
import com.mydomain.silverpay.data.model.mainDB.Gate;
import com.mydomain.silverpay.repository.MainDB.IGateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GateController {


    Logger logger = LoggerFactory.getLogger(GateController.class);

    final IGateRepository gateRepository;

    final IWalletRepository walletRepository;

    final UploadService uploadService;

    final WalletService walletService;


    //document approved required

    GateController(IGateRepository gateRepository, UploadService uploadService, IWalletRepository walletRepository, WalletService walletService) {

        this.gateRepository = gateRepository;
        this.uploadService = uploadService;
        this.walletRepository = walletRepository;
        this.walletService = walletService;
    }


    @GetMapping(Routes.Gate.get_gates)
    public ResponseEntity<?> getGates(@PathVariable String user_id,
                                      Principal principal,
                                      HttpServletRequest request) {


        List<Gate> gateList = gateRepository.findAll()
                .stream().filter(g -> g.getWallet().getUser().getId().equals(user_id))
                .sorted(Comparator.comparing(Gate::isActive))
                .collect(Collectors.toList());

        List<Wallet> walletList = walletRepository.findAll()
                .stream().filter(w -> w.getUser().getId().equals(user_id))
                .sorted(Comparator.comparing(Wallet::isMain))
                .sorted(Comparator.comparing(Wallet::isSms))
                .collect(Collectors.toList());


        GatesWalletsReturnDto result = new GatesWalletsReturnDto();

        result.setGateReturnDtoList(
                GateMapper.instance.gateToDto(gateList)
        );

        result.setWalletReturnDtoList(
                WalletMapper.instance.walletMapper(walletList)
        );


        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }


//        List<GateReturnDto> returnDtos = GateMapper.instance.gateToDto(gateList);


        return new ResponseEntity<>(result, HttpStatus.OK);

    }



    @GetMapping(Routes.Gate.get_gate)
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


    @PostMapping(Routes.Gate.insert_gate)
    public ResponseEntity<?> changeUserWallet(
            @ModelAttribute GateCreateDto createDto
            , HttpServletRequest request,
            @PathVariable String user_id) throws URISyntaxException, FileNotFoundException {


        Gate repoGate = gateRepository.findByWebsiteUrlAndWallet_User_Id(createDto.getWebsiteUrl(), user_id);

        if (repoGate != null)
            return new ResponseEntity<>("the gate registered before", HttpStatus.BAD_REQUEST);

        Gate gate = new Gate();
        gate.getWallet().setId(createDto.getWalletId());

        if (!createDto.getFile().isEmpty()) {

            if (createDto.getFile().getSize() > 0) {

                FileUploadedDto uploadResult =
                        uploadService.uploadProfilePicToLocal(createDto.getFile(), "", user_id, "gate");

                if (uploadResult.isStatus()) {
                    gate.setIconUrl(uploadResult.getUrl());
                } else {
                    return new ResponseEntity<>("error in gate insert(upload error)", HttpStatus.BAD_REQUEST);

                }
            } else {

                gate.setIconUrl("classPath:static/content/pic/logo-gate.png");//default logo path

            }
            gateRepository.save(gate);
            GateReturnDto returnDto = GateMapper.instance.gateToDto(gate);
            return ResponseEntity.created(new URI("{id}")).body(returnDto);

        } else {

            return new ResponseEntity<>("error in gate insert(upload error)", HttpStatus.BAD_REQUEST);

        }

    }


    @PutMapping(Routes.Gate.update_gate)
    public ResponseEntity<?> updateGate(
            @PathVariable String id
            , @ModelAttribute GateCreateDto createDto
            , Principal principal
            , @PathVariable String user_id) throws FileNotFoundException {

        Gate repoGate = gateRepository.findById(id).orElse(null);

        if (repoGate == null)
            return new ResponseEntity<>("no gate found", HttpStatus.NOT_FOUND);

        if (!principal.getName().equals(repoGate.getWallet().getUser().getId())) {
            return new ResponseEntity<>("UnAuthorize access detected", HttpStatus.UNAUTHORIZED);

        }

        if (createDto.getFile() != null) {

            if (createDto.getFile().getSize() > 0) {

                FileUploadedDto uploadResult =
                        uploadService.uploadProfilePicToLocal(createDto.getFile(), "", user_id, "gate");

                if (uploadResult.isStatus()) {
                    repoGate.setIconUrl(uploadResult.getUrl());
                } else {
                    return new ResponseEntity<>("error in file uploading", HttpStatus.BAD_REQUEST);
                }
            }
        }

        repoGate = GateMapper.instance.dtoToGate(createDto);

        gateRepository.save(repoGate);

        return new ResponseEntity<>(repoGate, HttpStatus.OK);


    }


    @PutMapping(Routes.Gate.activeDirectGate)
    public ResponseEntity<?> activeDirectGate(
            @PathVariable String user_id,
            @PathVariable String id
            , @ModelAttribute ActiveDirectGateDto directGateDto,
            Principal principal
    ) throws FileNotFoundException {

        Gate repoGate = gateRepository.findById(id).orElse(null);

        if (repoGate == null)
            return new ResponseEntity<>("no gate found", HttpStatus.NOT_FOUND);

        if (!principal.getName().equals(repoGate.getWallet().getUser().getId())) {
            return new ResponseEntity<>("UnAuthorize access detected", HttpStatus.UNAUTHORIZED);

        }

        if (directGateDto.isDirect()) {
            if (walletService.checkInventory(20000, directGateDto.getWallet_id())) {

                ReturnMessage decResult = walletService.decrease(20000, directGateDto.getWallet_id(), false);

                if (decResult.isStatus()) {
                    repoGate.setDirect(directGateDto.isDirect());
                    var updateResult = gateRepository.save(repoGate);
                    if (updateResult != null)
                        return ResponseEntity.noContent().build();
                    else {
                        ReturnMessage incResult = walletService.increase(20000, directGateDto.getWallet_id(), false);

                        if (incResult.isStatus()) {
                            return ResponseEntity.badRequest().body("error in register data");
                        } else {
                            return ResponseEntity.badRequest().body("error in register data,if amount decreased contact support");
                        }
                    }


                } else {
                    return ResponseEntity.badRequest().body(decResult.getMessage());
                }

            } else {
                return new ResponseEntity<>("no enough inventory", HttpStatus.BAD_REQUEST);

            }
        } else {
            repoGate.setDirect(directGateDto.isDirect());
            Gate updateResult = gateRepository.save(repoGate);
            if (updateResult != null)
                return ResponseEntity.noContent().build();
            else {

                return ResponseEntity.badRequest().body("error in register data");

            }


        }

    }
}

