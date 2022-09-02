package com.mydomain.silverpay.data.dto.site.panel.bank_card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankCardUserDetailDto {

    private String id;

    private boolean approve;

    private String bankName;

    private String ownerName;

    private String shaba;

    private String accountNumber;

    private String cardNumber;

    private String expireMonth;

    private String expireYear;

}
