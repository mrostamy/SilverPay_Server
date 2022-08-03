package com.mydomomain.silverpay.dto.site.panel.bank_card;

import com.mydomomain.silverpay.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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
