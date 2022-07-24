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

    @NotNull(message = "name is required")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "email format is invalid")
    private String username;
    @NotNull(message = "password is required")
    @Length(min = 4, max = 10, message = "password must be between 4 to 10 character")
    private String password;
    @NotNull(message = "phoneNumber is required")
    private String phoneNumber;


}
