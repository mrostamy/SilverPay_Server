package com.mydomomain.silverpay.dto.site.panel.users;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class EasyPayReturnDto {

    private String id;

    @NotEmpty(message = "walletGateId is required")
    private String walletGateId;

    private String gateId;
    @NotEmpty(message = "= is required")
    @Length(min = 4, max = 50, message = "name is between 4 to 50 character")
    private String name;
    @NotEmpty(message = "price is required")
    @Length(min = 0, max = 15, message = "price is between 4 to 50 character")
    private String price;
    @NotEmpty(message = "text is required")
    @Length(min = 0, max = 250, message = "price is between 0 to 250 character")
    private String text;
    @NotEmpty(message = "isCoupon is required")
    private boolean isCoupon;
    @NotEmpty(message = "isUserEmail is required")
    private boolean isUserEmail;
    @NotEmpty(message = "isUserName is required")
    private boolean isUserName;
    @NotEmpty(message = "isUserPhone is required")
    private boolean isUserPhone;
    @NotEmpty(message = "isUserEmailRequired is required")
    private boolean isUserEmailRequired;
    @NotEmpty(message = "isUserNameRequired is required")
    private boolean isUserNameRequired;
    @NotEmpty(message = "isUserPhoneRequired is required")
    private boolean isUserPhoneRequired;
    @NotEmpty(message = "isUserTextRequired is required")
    private boolean isUserTextRequired;
    @NotEmpty(message = "userNameExplain is required")
    @Length(min = 0, max = 25, message = "userNameExplain is between 0 to 25 character")
    private String userNameExplain;
    @NotEmpty(message = "userEmailExplain is required")
    @Length(min = 0, max = 25, message = "userEmailExplain is between 0 to 25 character")
    private String userEmailExplain;
    @NotEmpty(message = "userPhoneExplain is required")
    @Length(min = 0, max = 25, message = "userPhoneExplain is between 0 to 25 character")
    private String userPhoneExplain;
    @NotEmpty(message = "userTextExplain is required")
    @Length(min = 0, max = 25, message = "userTextExplain is between 0 to 25 character")
    private String userTextExplain;
    @NotEmpty(message = "isCountLimit is required")
    private boolean isCountLimit;
    @NotEmpty(message = "countLimit is required")
    private String countLimit;

    private String returnSuccess;

    private String returnFail;

    @NotEmpty(message = "isWallet is required")
    private boolean isWallet;
}
