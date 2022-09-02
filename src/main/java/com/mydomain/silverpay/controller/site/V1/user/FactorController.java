package com.mydomain.silverpay.controller.site.V1.user;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.data.dto.common.FactorPaginationDto;
import com.mydomain.silverpay.data.dto.site.panel.PaginationDto;
import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import com.mydomain.silverpay.repository.FinancialDB.IFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FactorController
{
    final IFactorRepository factorRepository;

    public FactorController(IFactorRepository factorRepository) {
        this.factorRepository = factorRepository;
    }

    @GetMapping(Routes.UserFactors.get_factors)
    public ResponseEntity<Object> getGatesFactors(@PathVariable String user_id, @PathVariable String gate_id
            , PaginationDto paginationDto) {

        //way 2
        // Gate gateRepo=gateRepository.findbyId(gate_id).orElse(null);

        //if not null check user id with gate user id if true ok

        List<Factor> factors = factorRepository.findAll()
                .stream().filter(factor -> factor.getWalletId().equals(gate_id)).collect(Collectors.toList());//apply pagination and sorting


        return new ResponseEntity<>(factors, HttpStatus.OK);

    }

    @GetMapping(Routes.UserFactors.get_factor)
    public ResponseEntity<Object> getFactor( @PathVariable String user_id,FactorPaginationDto paginationDto, @PathVariable String factor_id) {

        Factor factor= factorRepository.findById(factor_id).orElse(null);//apply pagination and sorting

        if(factor!=null){
             if(!factor.getUserId().equals(user_id)){
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unAuthorize access detected");
             }
        }


        return new ResponseEntity<>(factor, HttpStatus.OK);

    }

}
