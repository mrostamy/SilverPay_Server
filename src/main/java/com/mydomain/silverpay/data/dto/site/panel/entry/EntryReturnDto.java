package com.mydomain.silverpay.data.dto.site.panel.entry;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntryReturnDto {


    private String expireMonth;

    private String expireYear;


    private boolean isApprove;

    private boolean isPayed;

    private boolean isReject;

    private int price;

    private String textForUser;

    private String bankName;



    private String ownerName;


    private String shaba;


    private String cardNumber;


    private String walletName;


}
