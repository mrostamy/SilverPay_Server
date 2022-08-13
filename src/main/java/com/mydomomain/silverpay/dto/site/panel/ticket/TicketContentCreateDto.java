package com.mydomomain.silverpay.dto.site.panel.ticket;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TicketContentCreateDto {

    @NotNull(message = "text is required")
    @Length(min = 0,max = 1000,message = "text is between 0 to 1000 character")
    private String text;

    private MultipartFile file;

}
