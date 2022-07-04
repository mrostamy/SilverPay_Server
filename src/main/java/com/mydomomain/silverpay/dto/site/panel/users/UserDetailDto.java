package com.mydomomain.silverpay.dto.site.panel.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDto {

    private String id;

    private String name;

    private String username;

    private String phoneNumber;

    private String Address;

    private String gender;

    private int age;

    private String lastActive;

    private String city;

    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
