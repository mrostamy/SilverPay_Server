package com.mydomain.silverpay.data.dto.site.panel.gate;

import com.mydomain.silverpay.data.dto.site.panel.upload.users.WalletReturnDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GateWalletsReturnDto {

    private GateReturnDto gate;

    private List<WalletReturnDto> walletList;


}
