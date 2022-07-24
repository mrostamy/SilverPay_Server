package com.mydomomain.silverpay.model;

import com.mydomomain.silverpay.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Notification  extends BaseEntity<String> {

    public Notification() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull(message = "enter email is required")
    private boolean enterEmail;

    @NotNull(message = "enter sms is required")
    private boolean enterSms;

    @NotNull(message = "enter telegram is required")
    private boolean enterTelegram;

    @NotNull(message = "exit email is required")
    private boolean exitEmail;

    @NotNull(message = "exit sms is required")
    private boolean exitSms;

    @NotNull(message = "exit telegram is required")
    private boolean exitTelegram;


    @NotNull(message = "ticket email is required")
    private boolean ticketEmail;

    @NotNull(message = "ticket sms is required")
    private boolean ticketSms;

    @NotNull(message = "ticket telegram is required")
    private boolean ticketTelegram;


    @NotNull(message = "login email is required")
    private boolean loginEmail;

    @NotNull(message = "login sms is required")
    private boolean loginSms;

    @NotNull(message = "login telegram is required")
    private boolean loginTelegram;


    @OneToOne
    private User user;



}
