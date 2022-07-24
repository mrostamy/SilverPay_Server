package com.mydomomain.silverpay.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseEntity<String> {

    @NotNull(message = "client id is required")
    private String client_id;

    @NotNull(message = "user id is required")
    @Transient
    private String user_id;

    @NotNull(message = "value is required")
    private String value;

    @NotNull(message = "expire date is required")
    private Date expireTime;

    @OneToOne
    public User user;


}
