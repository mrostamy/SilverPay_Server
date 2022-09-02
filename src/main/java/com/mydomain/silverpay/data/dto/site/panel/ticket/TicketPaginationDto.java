package com.mydomain.silverpay.data.dto.site.panel.ticket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketPaginationDto {

    private boolean isAdminSide;

    private int closed;

    private int department;

    private short level;

    private long minDate;

    private long maxDate;
}
