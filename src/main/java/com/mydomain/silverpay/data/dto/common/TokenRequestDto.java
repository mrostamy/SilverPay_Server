package com.mydomain.silverpay.data.dto.common;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TokenRequestDto {

    @NotNull(message = "grant type is required")
    private String grant_type;

    @NotNull(message = "client id is required")
    private String client_id;

    @NotNull(message = "username is required")
    @Email(message = "email format is invalid")
    private String username;

    private String password;

    private String refreshToken;

    private boolean remember;
}
