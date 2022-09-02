package com.mydomain.silverpay.data.dto.site.panel.upload.users;

import com.mydomain.silverpay.data.dto.site.panel.gate.GateReturnDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GatesWalletsReturnDto {

    private List<GateReturnDto> gateReturnDtoList;

    private List<WalletReturnDto> WalletReturnDtoList;


}
