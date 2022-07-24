package com.mydomomain.silverpay.dto.common;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TokenResponseDto {

    private String token;

    private String refreshToken;

}
