package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.document.DocumentCreateDto;
import com.mydomomain.silverpay.dto.site.panel.document.DocumentReturnDto;
import com.mydomomain.silverpay.dto.site.panel.ticket.TicketContentCreateDto;
import com.mydomomain.silverpay.dto.site.panel.ticket.TicketCreateDto;
import com.mydomomain.silverpay.model.Document;
import com.mydomomain.silverpay.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper instance= Mappers.getMapper(TicketMapper.class);


    Ticket dtoToTicket (TicketCreateDto dto);

    Ticket dtoToTicket (TicketContentCreateDto dto);

}
