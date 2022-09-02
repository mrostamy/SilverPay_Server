package com.mydomain.silverpay.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReturnMessage {

    private boolean status;
    private String message;
    private String title;

}
