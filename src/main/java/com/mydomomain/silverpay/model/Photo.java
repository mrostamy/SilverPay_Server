package com.mydomomain.silverpay.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Photo extends BaseEntity<String> {


    public Photo() {
        this.setId(UUID.randomUUID().toString());
    }


    @NotNull
    private String url;
    private String alt;
    private String description;
    @NotNull
    private boolean isMain;

    @NotNull
    private String user_id;

    @OneToOne
    private User user;


}
