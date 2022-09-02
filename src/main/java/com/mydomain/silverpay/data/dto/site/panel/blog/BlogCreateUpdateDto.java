package com.mydomain.silverpay.data.dto.site.panel.blog;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BlogCreateUpdateDto {

    @NotEmpty(message = "blogGroupId is required")
    private String blogGroupId;

    @NotEmpty(message = "blog group name is required")
    @Length(max = 500,message = "title max character  is 500")
    private String title;

    @NotEmpty(message = "blog group name is required")
    @Length(max = 500,message = "tag max character  is 500")
    private String tag;

    private MultipartFile file;

    @NotEmpty(message = "text is required")
    private String text;


    @NotEmpty(message = "summeryText is required")
    private String summeryText;

}
