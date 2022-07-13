package com.mydomomain.silverpay.dto.site.panel.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PasswordResetDto {

    @NotNull
    private String oldPassword;

    @NotNull
    @Length(min = 4,max = 10,message = "password must be between 4 to 10 character")
    private String newPassword;

}
