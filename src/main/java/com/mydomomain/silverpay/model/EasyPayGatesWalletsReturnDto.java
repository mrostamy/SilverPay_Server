package com.mydomomain.silverpay.model;

import com.mydomomain.silverpay.dto.site.panel.users.EasyPayReturnDto;
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
