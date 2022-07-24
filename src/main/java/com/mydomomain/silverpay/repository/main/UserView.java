package com.mydomomain.silverpay.repository.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mydomomain.silverpay.model.Role;

import java.util.List;

public interface UserView {

    @JsonProperty(index = -3)
    String getId();

    @JsonProperty(index = -2)
    String getUsername();

    List<Role> getRoles();

}
