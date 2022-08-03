package com.mydomomain.silverpay.controller.site.V1.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletCreateDto {

    private boolean isMain=false;

    private boolean isSms=false;

    private String name;

    private int inventory=0;

    private int enterMoney=0;

    private int exitMoney=0;

    private int onExitMoney=0;
}
