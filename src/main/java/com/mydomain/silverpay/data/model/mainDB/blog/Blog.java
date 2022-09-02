package com.mydomain.silverpay.data.model.mainDB.blog;


import com.mydomain.silverpay.data.model.BaseEntity;
import com.mydomain.silverpay.data.model.mainDB.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
public class Blog extends BaseEntity<String> {

    @NotEmpty(message = "blog group name is required")
    @Length(max = 500,message = "title max character  is 500")
    private String title;

    @NotEmpty(message = "blog group name is required")
    @Length(max = 500,message = "tag max character  is 500")
    private String tag;

    @NotEmpty(message = "blog group name is required")
    @Length(max = 500,message = "pic address max character  is 500")
    private String picAddress;

    @NotEmpty(message = "text is required")
    private String text;

    @NotEmpty(message = "status is required")
    private boolean status;

    @NotEmpty(message = "summeryText is required")
    private String summeryText;

    @NotEmpty(message = "isSelected is required")
    private boolean isSelected;

    @NotEmpty(message = "viewCountis required")
    private int viewCount;

    @OneToOne
    private User user;

    @OneToOne
    private BlogGroup blogGroup;


}
