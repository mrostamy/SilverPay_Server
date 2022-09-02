package com.mydomain.silverpay.data.dto.site.panel.gate;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class GateCreateDto {

        @NotEmpty(message = "isIp is required")
        private boolean isIp;

        @NotEmpty(message = "websiteName is required")
        @Length(max = 100, message = "maximum size of websiteName is 100 ")
        private String websiteName;

        @NotEmpty(message = "websiteName is required")
        @Length(max = 500, message = "maximum size of ip is 100 ")
        private String websiteUrl;

        @NotEmpty(message = "websiteName is required")
        @Length(max = 10, message = "maximum size of phoneNumber is 10 ")
        private String phoneNumber;

        @NotEmpty(message = "websiteName is required")
        @Length(max = 100, message = "maximum size of text is 1000 ")
        private String text;

        @NotEmpty(message = "websiteName is required")
        @Length(max = 50, message = "maximum size of grp is 50 ")
        private String grp; //group

        @NotEmpty(message = "wallet id is required")
        private String walletId;

        private MultipartFile file;


}
