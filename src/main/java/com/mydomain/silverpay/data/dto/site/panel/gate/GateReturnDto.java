package com.mydomain.silverpay.data.dto.site.panel.gate;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GateReturnDto {


    private String id;

    private String websiteName;

    private boolean isIp;

    private boolean isActive = false;

    private boolean isDirect = false;

    private String websiteUrl;

    private String text;

    private String grp; //group

    private String iconUrl;

}
