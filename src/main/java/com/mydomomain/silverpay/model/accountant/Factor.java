package com.mydomomain.silverpay.model.accountant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mydomomain.silverpay.model.BankCard;
import com.mydomomain.silverpay.model.BaseEntity;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.model.Wallet;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Factor extends BaseEntity<String> {

    @NotNull
    @Length(max = 100)
    private String userName;

    private boolean status;

    private int giftCode;

    private boolean isGifted;

    @NotNull
    private int price;

    @NotNull
    private int endPrice;

    @NotNull
    @Length(min = 0, max = 500)
    private String refBank;

    @NotNull
    private short kind;

    @NotNull
    private short bank;


    @NotEmpty
    private String userId;

    @NotEmpty
    private String gateId;

    @NotEmpty
    private String walletId;

}
