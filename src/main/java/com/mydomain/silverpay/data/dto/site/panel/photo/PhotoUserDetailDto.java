package com.mydomain.silverpay.data.dto.site.panel.photo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoUserDetailDto {

    private String id;
    private String photoUrl;
    private String description;
    private String alt;
    private boolean isMain;

}
