package com.mydomain.silverpay.data.dto.site.panel.photo;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PhotoProfileDto {

    private String url;
    private MultipartFile file;
    private String publicId;
    private String description="profile pic";
    private boolean isMain=true;
    private String alt = "profile pic";

}
