package com.mydomain.silverpay.data.dto.site.panel.upload.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class UserLoginDto {

    @NotNull
    @Email(message = "email format is invalid")
    private String username;
    @NotNull
    @Length(min = 4, max = 10, message = "password must be between 4 to 10 character")
    private String password;

    private boolean remember;
}
