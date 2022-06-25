package com.mydomomain.silverpay.model;

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
public class BankCard extends BaseEntity<String> {
    public BankCard() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull
    private String bankName;

//    private String ownerName;

    private String shaba;

    @NotNull
    @Length(min = 16,max = 16)
    private String cardNumber;

    @NotNull
    @Length(min = 2,max = 2)
    private String expireMonth;

    @NotNull
    @Length(min = 2,max = 2)
    private String expireYear;

//    private boolean approve;


    private String user_id;

    @OneToOne
    private User user;



}
