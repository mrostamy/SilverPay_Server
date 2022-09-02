package com.mydomain.silverpay.data.dto.site.panel.accountant;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EditFactorDto {


    @NotNull
    @Length(min = 0, max = 500)
    private String refBank;

}
