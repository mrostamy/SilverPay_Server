package com.mydomain.silverpay.controller.site.V1.accountant;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.BankCardMapper;
import com.mydomain.silverpay.configuration.model_mapper.GateMapper;
import com.mydomain.silverpay.data.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomain.silverpay.data.dto.site.panel.gate.GateReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.gate.GateStatusDto;
import com.mydomain.silverpay.data.model.mainDB.BankCard;
import com.mydomain.silverpay.data.model.mainDB.Gate;
import com.mydomain.silverpay.repository.MainDB.IGateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController("accountant_gate")
public class GateController {

    final IGateRepository gateRepository;

    public GateController(IGateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    @GetMapping(Routes.Accountant.getGates)
    public ResponseEntity<?> getGates(
            Principal principal,
            HttpServletRequest request) {


        List<Gate> gates = gateRepository.findAll();

//        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
//            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
//            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
//        }
//

        List<GateReturnDto> returnWallets = GateMapper.instance.gateToDto(gates);


        return new ResponseEntity<>(returnWallets, HttpStatus.OK);

        //apply pagination and sorting

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
