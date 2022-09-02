package com.mydomain.silverpay.data.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateTokenDto {

    private boolean status;

    private String accessToken;

    private String refreshToken;

    private String message;
}