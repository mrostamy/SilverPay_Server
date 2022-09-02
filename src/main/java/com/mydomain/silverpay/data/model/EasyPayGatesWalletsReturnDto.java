package com.mydomain.silverpay.data.model;

import com.mydomain.silverpay.data.dto.site.panel.upload.users.EasyPayReturnDto;
import com.mydomain.silverpay.data.model.mainDB.Gate;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EasyPayGatesWalletsReturnDto {

    private EasyPayReturnDto easyPay;

    private List<Gate> gates;

    private List<Wallet> wallets;



}
