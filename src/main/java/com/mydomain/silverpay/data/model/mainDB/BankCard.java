package com.mydomain.silverpay.data.model.mainDB;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@Entity
public class BankCard extends BaseEntity<String> implements Serializable {
    public BankCard() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull
    @Length(min = 0,max = 50)
    private String bankName;

    @NotNull
    @Length(min = 0,max = 100)
    private String ownerName;

    private String shaba;

    private String accountNumber;

    @NotNull
    @Length(min = 16,max = 16)
    private String cardNumber;

    @NotNull
    @Length(min = 2,max = 2)
    private String expireMonth;

    @NotNull
    @Length(min = 2,max = 2)
    private String expireYear;

    @NotNull
    private boolean approve;

    @OneToOne
    @JsonBackReference
    private User user;



}
