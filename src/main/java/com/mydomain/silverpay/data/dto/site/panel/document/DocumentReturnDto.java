package com.mydomain.silverpay.data.dto.site.panel.document;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DocumentReturnDto {

    private String id;

    private boolean isTrue;

    private String name;

    private String family;

    private String message;

    private String fatherName_registerCode;

    private String DateOfBirth;

    private String dateCreated;

    private String photoUrl;

    private String Address;

    private short approve;//1 => approved ; 0 => in check


    private String nationalCode;

    private String userId;

    private String userName;

}
