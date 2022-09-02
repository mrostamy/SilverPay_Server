package com.mydomain.silverpay.data.dto.site.panel.gate;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GateStatusDto

{
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
