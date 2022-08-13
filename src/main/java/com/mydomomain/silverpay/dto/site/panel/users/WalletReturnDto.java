package com.mydomomain.silverpay.dto.site.panel.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WalletReturnDto {

    private String id;

    private boolean isMain;

    private boolean isSms;

    private boolean isBlock;

    private String name;

    private String code;

    private int inventory;

    private int enterMoney;

    private int exitMoney;

    private int onExitMoney;


}
