package com.mydomain.silverpay.data.dto.site.panel.photo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PhotoReturnProfileDto {

    private String url;
    private String description="profile pic";
    private String alt = "profile pic";


}
