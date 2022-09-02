package com.mydomain.silverpay.data.dto.site.panel.upload.users;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserBlogInfoDto {


    private String name;

    private int totalBlogs;

    private int approveBlogs;

    private int unApproveBlogs;


}
