package com.mydomain.silverpay.data.dto.site.panel.notification;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class NotificationUpdateDto {


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


}
