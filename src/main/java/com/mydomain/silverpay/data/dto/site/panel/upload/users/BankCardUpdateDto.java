package com.mydomain.silverpay.data.dto.site.panel.upload.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankCardUpdateDto {
    private String bankName;

    private String ownerName;

    private String shaba;

    private String accountNumber;

    private String cardNumber;

    private String expireMonth;

    private String expireYear;

    private boolean approve;
}
