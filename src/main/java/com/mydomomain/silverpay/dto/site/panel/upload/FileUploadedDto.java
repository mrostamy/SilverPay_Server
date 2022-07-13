package com.mydomomain.silverpay.dto.site.panel.upload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadedDto {

    private boolean status;
    private boolean localUpLoaded;
    private String url;
    private String publicId = "0";
    private String message;
}
