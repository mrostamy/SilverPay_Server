package com.mydomomain.silverpay.model.accountant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mydomomain.silverpay.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Entry extends BaseEntity<String> {

    public Entry() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull
    private boolean isApprove;

    @NotNull
    private boolean isPayed;

    @NotNull
    private boolean isReject;

    @NotNull
    private int price;

    @NotNull
    @Length(max = 1000)
    private String textForUser;

    @NotNull
    @Length(min = 0,max = 50)
    private String bankName;


    @NotNull
    @Length(min = 0,max = 100)
    private String ownerName;


    private String shaba;


    @NotNull
    @Length(min = 16,max = 16)
    private String cardNumber;


    @NotNull
    @Length(max = 20)
    private String walletName;

    @NotNull
    private String userId;

    @NotNull
    private String bankCardId;

    @NotNull
    private String walletId;

}
