package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.dto.site.panel.ticket.TicketContentCreateDto;
import com.mydomain.silverpay.data.dto.site.panel.ticket.TicketCreateDto;
import com.mydomain.silverpay.data.model.mainDB.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper instance= Mappers.getMapper(TicketMapper.class);


    Ticket dtoToTicket (TicketCreateDto dto);

    Ticket dtoToTicket (TicketContentCreateDto dto);

}
