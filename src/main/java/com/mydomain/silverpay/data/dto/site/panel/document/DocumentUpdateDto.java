package com.mydomain.silverpay.data.dto.site.panel.document;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DocumentUpdateDto {

    @Length(min = 0, max = 100, message = "message must be less than 30 character")
    private String message;


    @NotNull(message = "approve is required")
    private short approve;//1 => approved ; 0 => in check
}
