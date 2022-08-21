package com.mydomomain.silverpay.dto.site.panel.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntryStateChange {


    private boolean isApprove;

    private boolean isPayed;

    private boolean isReject;


}
