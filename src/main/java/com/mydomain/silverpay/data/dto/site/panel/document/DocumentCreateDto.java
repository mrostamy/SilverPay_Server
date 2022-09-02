package com.mydomain.silverpay.data.dto.site.panel.document;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DocumentCreateDto {

    @NotNull(message = "hagigi or hogogi is required")
    private boolean isTrue;

    @NotNull(message = "name is required")
    @Length(min = 0, max = 30,message = "name must be less than 30 character")
    private String name;

    @NotNull(message = "family is required")
    @Length(min = 0, max = 30,message = "family must be less than 30 character")
    private String family;

    @NotNull(message = "fatherName or registerCode is required")
    @Length(min = 0, max = 30,message = "fatherName must be less than 30 character")
    private String fatherName_registerCode;

    @NotNull(message = "DateOfBirth is required")
    private String DateOfBirth;


    private MultipartFile file;


    @NotNull(message = "Address is required")
    @Length(min = 0, max = 500,message = "Address must be less than 500 character")
    private String Address;


    @NotNull(message = "nationalCode is required")
    @Length(min = 10, max = 10)
    private String nationalCode;


}
