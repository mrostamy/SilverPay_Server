package com.mydomomain.silverpay.dto.site.panel.gate;


import com.mydomomain.silverpay.model.BaseEntity;
import com.mydomomain.silverpay.model.Wallet;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

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
