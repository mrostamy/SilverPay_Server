package com.mydomomain.silverpay.dto.site.panel.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class EasyPayCreateUpdateDto {

    private String id;

    private String walletGateId;

    private String gateId;
    private String name;
    private String price;
    private String text;
    private boolean isCoupon;
    private boolean isUserEmail;
    private boolean isUserName;
    private boolean isUserPhone;
    private boolean isUserEmailRequired;
    private boolean isUserNameRequired;
    private boolean isUserPhoneRequired;
    private boolean isUserTextRequired;
    private String userNameExplain;
    private String userEmailExplain;
    private String userPhoneExplain;
    private String userTextExplain;
    private boolean isCountLimit;
    private int countLimit;

    private String returnSuccess;

    private String returnFail;

    private boolean isWallet;

}
