package com.mydomain.silverpay.data.model.mainDB;

import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Setting extends BaseEntity<Integer> {

    @NotNull
    private String  cloudinaryName;

    @NotNull
    private String cloudinaryApiKey;

    @NotNull
    private String cloudinaryApiSecret;

    @NotNull
    private boolean uploadLocal;

}
