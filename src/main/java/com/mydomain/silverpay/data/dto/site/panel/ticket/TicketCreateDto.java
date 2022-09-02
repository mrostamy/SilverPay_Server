package com.mydomain.silverpay.data.dto.site.panel.ticket;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TicketCreateDto {

    @NotNull(message = "title is required")
    @Length(min = 0, max = 50, message = "title must be between 0 to 50 character")
    private String title;

    @NotNull(message = "department is required")
    @Length(min = 0, max = 50, message = "department must be between 0 to 50 character")
    private String department;


    @NotNull(message = "level is required")
    private short level;



}
