package com.mydomomain.silverpay.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Photo extends BaseEntity<String> implements Serializable {


    public Photo() {
        this.setId(UUID.randomUUID().toString());
    }


    @NotNull
    @Length(min = 0, max = 1000)
    private String photoUrl;
    @Length(min = 0, max = 500)
    private String alt;
    @Length(min = 0, max = 500)
    private String description;
    @NotNull
    private boolean isMain;

    private String publicId;

    @OneToOne
    @JsonBackReference
    private User user;

    public boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(boolean main) {
        isMain = main;
    }
}
