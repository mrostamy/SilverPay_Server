package com.mydomain.silverpay.data.dto.site.panel.accountant;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AccountantUserDto {

    private String id;

    private String name;

    private String username;

    private String phoneNumber;

    private String gender;

    private int age;

    private String photoUrl;

    private int inventorySum;

    private int exitMoneySum;

    private int enterMoneySum;

    private int onExitMoneySum;

}
