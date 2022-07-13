package com.mydomomain.silverpay.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Setting extends BaseEntity<Integer>{

    @NotNull
    private String  cloudinaryName;

    @NotNull
    private String cloudinaryApiKey;

    @NotNull
    private String cloudinaryApiSecret;

    @NotNull
    private boolean uploadLocal;

}
