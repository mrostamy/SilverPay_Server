package com.mydomain.silverpay.data.model.mainDB;

import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Wallet extends BaseEntity<String> {

    public Wallet() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull(message = "isMain is required")
    private boolean isMain;

    @NotNull(message = "isSms is required")
    private boolean isSms;

    @NotNull(message = "isBlock is required")
    private boolean isBlock;


    @NotNull(message = "name is required")
    @Length(min = 0, max = 20, message = "name must be between 0 to 20 character")
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

    @OneToMany
    @JoinColumn
    List<Gate> gates;


}