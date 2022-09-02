package com.mydomain.silverpay.data.model.mainDB;

import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Gate extends BaseEntity<String> {

    public Gate() {
        this.setId(UUID.randomUUID().toString());

    }

    @NotEmpty(message = "websiteName is required")
    @Length(max = 100, message = "maximum size of websiteName is 100 ")
    private String websiteName;

    @Length(max = 100, message = "maximum size of ip is 100 ")
    private String ip;

    @NotEmpty(message = "isIp is required")
    private boolean isIp;

    public boolean isIp() {
        return isIp;
    }

    public void setIp(boolean ip) {
        isIp = ip;
    }

    @NotEmpty(message = "isActive is required")
    private boolean isActive=false;

    @NotEmpty(message = "isDirect is required")
    private boolean isDirect=false;

    @NotEmpty(message = "websiteName is required")
    @Length(max = 500, message = "maximum size of ip is 100 ")
    private String websiteUrl;

    @NotEmpty(message = "websiteName is required")
    @Length(max = 10, message = "maximum size of phoneNumber is 10 ")
    private String phoneNumber;

    @NotEmpty(message = "websiteName is required")
    @Length(max = 100, message = "maximum size of text is 1000 ")
    private String text;

    @NotEmpty(message = "websiteName is required")
    @Length(max = 50, message = "maximum size of grp is 50 ")
    private String grp; //group

    @NotEmpty(message = "websiteName is required")
    @Length(max = 1000, message = "maximum size of iconUrl is 1000 ")
    private String iconUrl;


    @OneToOne
    Wallet wallet;


}
