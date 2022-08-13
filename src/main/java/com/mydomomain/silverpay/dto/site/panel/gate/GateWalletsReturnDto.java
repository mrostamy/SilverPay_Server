package com.mydomomain.silverpay.dto.site.panel.gate;

import com.mydomomain.silverpay.dto.site.panel.users.WalletReturnDto;
import com.mydomomain.silverpay.model.Gate;
import com.mydomomain.silverpay.model.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GateWalletsReturnDto {

    private GateReturnDto gate;

    private List<WalletReturnDto> walletList;


}
