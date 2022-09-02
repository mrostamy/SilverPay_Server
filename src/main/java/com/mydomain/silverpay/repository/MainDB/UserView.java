package com.mydomain.silverpay.repository.MainDB;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mydomain.silverpay.data.model.mainDB.Role;

import java.util.List;

public interface UserView {

    @JsonProperty(index = -3)
    String getId();

    @JsonProperty(index = -2)
    String getUsername();

    List<Role> getRoles();

}
