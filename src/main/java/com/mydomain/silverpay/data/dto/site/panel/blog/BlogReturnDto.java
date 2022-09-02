package com.mydomain.silverpay.data.dto.site.panel.blog;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BlogReturnDto {

    private String id;

    private String title;

    private String tag;

    private String picAddress;

    private String text;

    private boolean status;

    private String summeryText;

    private boolean isSelected;

    private int viewCount;

    private String userId;

    private String userName;

    private String name;

    private String blogGroupId;

    private String blogGroupName;


}
