package com.mydomomain.silverpay.dto.site.panel.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRegisterDto {

    @NotNull
    private String name;
    @NotBlank
    @Email(message = "email format is invalid")
    private String username;
    @NotNull
    @Length(min = 4, max = 10, message = "password must be between 4 to 10 character")
    private String password;
    @NotNull
    private String phoneNumber;



}
