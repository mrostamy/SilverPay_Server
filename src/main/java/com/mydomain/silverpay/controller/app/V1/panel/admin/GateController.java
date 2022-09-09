package com.mydomain.silverpay.controller.app.V1.panel.admin;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.controller.app.V1.panel.user.DocumentController;
import com.mydomain.silverpay.data.dto.app.panel.gate.GateStatusDto;
import com.mydomain.silverpay.data.model.mainDB.Gate;
import com.mydomain.silverpay.repository.MainDB.IDocumentRepository;
import com.mydomain.silverpay.repository.MainDB.IGateRepository;
import com.mydomain.silverpay.service.userService.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("admin_gateController")
public class GateController {

    Logger logger = LoggerFactory.getLogger(DocumentController.class);

    final IDocumentRepository documentRepository;

    final UploadService uploadService;

    final IGateRepository gateRepository;


    GateController(IDocumentRepository documentRepository, UploadService uploadService,IGateRepository gateRepository) {

        this.documentRepository = documentRepository;
        this.uploadService = uploadService;
        this.gateRepository = gateRepository;

    }

    @PutMapping(Routes.Accountant.change_direct_gate)
    public ResponseEntity<?> getUserGates(
            GateStatusDto directGateDto,
            @PathVariable String gate_id) {


        Gate gateRepo = gateRepository.findById(gate_id).orElse(null);

        if (gateRepo != null) {

            gateRepo.setDirect(directGateDto.isFlag());
            gateRepository.save(gateRepo);

            return ResponseEntity.noContent().build();


        }else{
            return ResponseEntity.badRequest().body("no gate found");

        }




    }

    @PutMapping(Routes.Accountant.change_direct_gate)
    public ResponseEntity<?> changeDirectGate(
            GateStatusDto directGateDto,
            @PathVariable String gate_id) {


        Gate gateRepo = gateRepository.findById(gate_id).orElse(null);

        if (gateRepo != null) {

            gateRepo.setDirect(directGateDto.isFlag());
            gateRepository.save(gateRepo);

            return ResponseEntity.noContent().build();


        }else{
            return ResponseEntity.badRequest().body("no gate found");

        }




    }

    @PutMapping(Routes.Accountant.change_active_gate)
    public ResponseEntity<?> changeActiveGate(
            GateStatusDto directGateDto,
            @PathVariable String gate_id) {


        Gate gateRepo = gateRepository.findById(gate_id).orElse(null);

        if (gateRepo != null) {

            gateRepo.setIp(directGateDto.isFlag());
            gateRepository.save(gateRepo);

            return ResponseEntity.noContent().build();


        }else{
            return ResponseEntity.badRequest().body("no gate found");

        }




    }


    @PutMapping(Routes.Accountant.change_ip_gate)
    public ResponseEntity<?> changeIpGate(
            GateStatusDto directGateDto,
            @PathVariable String gate_id) {


        Gate gateRepo = gateRepository.findById(gate_id).orElse(null);

        if (gateRepo != null) {

            gateRepo.setDirect(directGateDto.isFlag());
            gateRepository.save(gateRepo);

            return ResponseEntity.noContent().build();


        }else{
            return ResponseEntity.badRequest().body("no gate found");

        }




    }




}
