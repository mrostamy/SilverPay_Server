package com.mydomain.silverpay.controller.app.V1.panel.auth;

import com.mydomain.silverpay.data.dto.common.TokenRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TokenController {


    public ResponseEntity<?> auth(@RequestBody @Valid TokenRequestDto requestDto) {


        switch (requestDto.getGrant_type()) {

            case "passowrd": {
//                return generateNewToken();
                break;
            }

            case "refreshToken": {
//                return refreshToken();
                break;
            }

            default:
                return new ResponseEntity<>("error in authoriztions", HttpStatus.UNAUTHORIZED);


        }
        return null;
    }

}
