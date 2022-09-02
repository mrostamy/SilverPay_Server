package com.mydomain.silverpay.data.model.financialDB.accountant;

import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Entry extends BaseEntity<String> {

    public Entry() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotEmpty
    private boolean isApprove;

    @NotEmpty
    private boolean isPayed;

    @NotEmpty
    private boolean isReject;

    @NotEmpty
    private int price;

    @NotEmpty
    @Length(max = 1000)
    private String textForUser;

    @NotEmpty
    @Length(min = 0,max = 50)
    private String bankName;


    @NotEmpty
    @Length(min = 0,max = 100)
    private String ownerName;


    private String shaba;


    @NotEmpty
    @Length(min = 16,max = 16)
    private String cardNumber;


    @NotEmpty
    @Length(max = 20)
    private String walletName;

    @NotEmpty
    @Length(max = 200)
    private String bankTrackingCode;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String bankCardId;

    @NotEmpty
    private String walletId;

}
