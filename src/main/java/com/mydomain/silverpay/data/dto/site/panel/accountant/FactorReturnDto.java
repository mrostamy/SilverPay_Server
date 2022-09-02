package com.mydomain.silverpay.data.dto.site.panel.accountant;

import com.mydomain.silverpay.data.dto.site.panel.gate.GateReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.WalletReturnDto;
import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactorReturnDto {


    private Factor factor;

    private GateReturnDto gate;

    private WalletReturnDto wallet;




}
