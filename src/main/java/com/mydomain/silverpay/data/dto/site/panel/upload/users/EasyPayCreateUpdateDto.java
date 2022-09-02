package com.mydomain.silverpay.data.dto.site.panel.upload.users;

import lombok.Getter;
import lombok.Setter;

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

    public int getCountLimit() {
        return countLimit;
    }

    public boolean isCountLimit() {
        return !isCountLimit;
    }

    public void setCountLimit(boolean countLimit) {
        isCountLimit = countLimit;
    }

    public void setCountLimit(int countLimit) {
        this.countLimit = countLimit;
    }
}
