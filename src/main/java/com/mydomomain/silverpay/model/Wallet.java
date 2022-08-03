package com.mydomomain.silverpay.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Wallet extends BaseEntity<String>{

    public Wallet() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull(message = "isMain is required")
    private boolean isMain;

    @NotNull(message = "isSms is required")
    private boolean isSms;

    @NotNull(message = "name is required")
    @Length(min = 0,max = 50,message = "name must be between 0 to 50 character")
    private String name;

    @NotNull(message = "code is required")
    @UniqueElements
    private long code;

    @NotNull(message = "inventory is required")
    private int inventory;

    @NotNull(message = "enterMoney is required")
    private int enterMoney;

    @NotNull(message = "exitMoney is required")
    private int exitMoney;

    @NotNull(message = "onExitMoney is required")
    private int onExitMoney;


    @OneToOne
    private User user;


}
