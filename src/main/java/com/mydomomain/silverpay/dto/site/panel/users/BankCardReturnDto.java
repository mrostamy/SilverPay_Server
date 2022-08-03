package com.mydomomain.silverpay.dto.site.panel.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BankCardReturnDto {

    private String id;

    private String bankName;

    private String ownerName;

    private String shaba;

    private String accountNumber;

    private String cardNumber;

    private String expireMonth;

    private String expireYear;

    private boolean approve;

}
