package com.mydomomain.silverpay.dto.site.panel.users;

import com.mydomomain.silverpay.dto.site.panel.gate.GateReturnDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GatesWalletsReturnDto {

    private List<GateReturnDto> gateReturnDtoList;

    private List<WalletReturnDto> WalletReturnDtoList;


}
