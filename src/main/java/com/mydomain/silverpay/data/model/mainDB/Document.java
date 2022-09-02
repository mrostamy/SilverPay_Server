package com.mydomain.silverpay.data.model.mainDB;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Document extends BaseEntity<String> {

    public Document() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull(message = "hagigi or hogogi is required")
    private boolean isTrue;

    @NotNull(message = "name is required")
    @Length(min = 0, max = 30, message = "name must be less than 30 character")
    private String name;


    @NotNull(message = "family is required")
    @Length(min = 0, max = 30, message = "family must be less than 30 character")
    private String family;

    @Length(min = 0, max = 100, message = "message must be less than 30 character")
    private String message;

    @NotNull(message = "fatherName or registerCode is required")
    @Length(min = 0, max = 30, message = "fatherName must be less than 30 character")
    private String fatherName_registerCode;

    @NotNull(message = "DateOfBirth is required")
    private String DateOfBirth;

    @NotNull(message = "dateCreated is required")
    private String dateCreated;

    private String photoUrl;


    @NotNull(message = "Address is required")
    @Length(min = 0, max = 500, message = "Address must be less than 500 character")
    private String Address;

    @NotNull(message = "approve is required")
    private short approve;//1 => approved ; 0 => in check

    @NotNull(message = "nationalCode is required")
    @Length(min = 10, max = 10)
    private String nationalCode;


    @OneToOne
    @JsonBackReference
    private User user;

}
