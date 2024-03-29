package com.mydomain.silverpay.controller.app.V1.panel.admin;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.data.dto.app.panel.ticket.TicketContentCreateDto;
import com.mydomain.silverpay.data.dto.app.panel.ticket.TicketPaginationDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.FileUploadedDto;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.data.model.mainDB.Ticket;
import com.mydomain.silverpay.data.model.mainDB.TicketContent;
import com.mydomain.silverpay.repository.MainDB.ITicketContentRepository;
import com.mydomain.silverpay.repository.MainDB.ITicketRepository;
import com.mydomain.silverpay.service.userService.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController("admin_ticketController")
public class TicketController {

    //add admin role

    Logger logger = LoggerFactory.getLogger(com.mydomain.silverpay.controller.app.V1.panel.user.TicketController.class);

    final ITicketRepository ticketRepository;

    final ITicketContentRepository ticketContentRepository;

    final UploadService uploadService;

    TicketController(ITicketRepository ticketRepository, UploadService uploadService, ITicketContentRepository ticketContentRepository) {

        this.ticketRepository = ticketRepository;
        this.uploadService = uploadService;
        this.ticketContentRepository = ticketContentRepository;
    }


    @GetMapping(Routes.Ticket.get_ticket)
    public ResponseEntity<?> getTicket(@PathVariable String user_id,
                                       @PathVariable String id,
                                       Principal principal,
                                       HttpServletRequest request) {

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        } else {

            Ticket ticket = ticketRepository.findById(id).orElse(null);

            if (ticket != null) {

                return new ResponseEntity<>(ticket, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("no ticket found", HttpStatus.NOT_FOUND);

            }
        }


    }

    @GetMapping(Routes.Ticket.get_tickets)
    public ResponseEntity<?> getTickets(
            TicketPaginationDto paginationDto,
            @PathVariable String user_id,
            @RequestParam int page,
            Principal principal,
            HttpServletRequest request) {


        PageImpl<Ticket> ptickets = (PageImpl<Ticket>) ticketRepository.findAll(PageRequest.of(page, 10));

        System.out.println(ptickets);
        List<Ticket> tickets = ticketRepository.findAll()
                .stream().filter(b -> Objects.equals(b.getUser().getId(), user_id))
                .sorted(Comparator.comparing(Ticket::isClosed))
                .sorted(Comparator.comparing(Ticket::getModifiedAt))
                .collect(Collectors.toList());

        if (!principal.getName().equals(user_id) && request.isUserInRole("Admin")) {
            ReturnMessage returnMessage = new ReturnMessage(false, "UnAuthorize Access detected", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }


        return new ResponseEntity<>(tickets, HttpStatus.OK);

    }

//
//    @PostMapping(Routes.Ticket.insert_ticket)
//    public ResponseEntity<?> addTicket(
//            TicketCreateDto createDto,
//            HttpServletRequest request,
//            @PathVariable String user_id) throws URISyntaxException {
//
//
//        List<Ticket> tickets = ticketRepository.findAll().
//                stream()
//                .filter(b ->
//                        Objects.equals(b.getUser().getId(), user_id) && Objects.equals(b.getTitle(), createDto.getTitle()))
//                .collect(Collectors.toList());
//
//        if (tickets != null)
//            return new ResponseEntity<>("the ticket registered before", HttpStatus.BAD_REQUEST);
//
//
//        Ticket ticket = new Ticket();
//        ticket.setId(user_id);
//        ticket.setAdminSide(false);
//        ticket.setClosed(false);
//
//        ticket = TicketMapper.instance.dtoToTicket(createDto);
//
//
//        Ticket res = ticketRepository.save(ticket);
//
//        if (res == null) {
//            return new ResponseEntity<>("error in ticket save", HttpStatus.BAD_REQUEST);
//
//        }
//
//        return ResponseEntity.created(new URI("?id=" + user_id)).body(ticket);
//
//
//    }

    @PostMapping(Routes.Ticket.insert_ticket_content)
    public ResponseEntity<?> addTicketContent(
            TicketContentCreateDto createDto,
            HttpServletRequest request,
            @PathVariable String user_id,
            @PathVariable String id) throws URISyntaxException, FileNotFoundException {

        TicketContent ticket = new TicketContent();
        ticket.getTicket().setId(id);
        ticket.setAdminSide(false);
        ticket.setText(createDto.getText());

        String url = "";
        if (!createDto.getFile().isEmpty()) {

            FileUploadedDto uploadResult = uploadService.uploadProfilePicToLocal(createDto.getFile(), request.getContextPath(), user_id, "");
            if (uploadResult.isStatus()) {
                url = uploadResult.getUrl();


            } else {
                return new ResponseEntity<>(uploadResult.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {

            ticket.setFileUrl("");
        }


        TicketContent result = ticketContentRepository.save(ticket);

        if (result != null) {

            return ResponseEntity.created(new

                            URI("?id=" + user_id)).

                    body(ticket);
        } else {
            return new ResponseEntity<>("error in save of ticket content", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping(Routes.Ticket.get_ticket_content)
    public ResponseEntity<?> getTicketContent(@PathVariable String id,
                                              @PathVariable String user_id,
                                              Principal principal) {

        TicketContent fromRepo = ticketContentRepository.findById(id).orElse(null);

        if (fromRepo != null) {

            if (principal.getName().equals(user_id)) {

                return new ResponseEntity<>(fromRepo, HttpStatus.OK);

            } else {
                logger.info("unAuthorize access detected");
                return new ResponseEntity<>("unAuthorized access detected", HttpStatus.UNAUTHORIZED);
            }

        } else {

            return new ResponseEntity<>("no ticket content found", HttpStatus.NOT_FOUND);

        }

    }

    //set ticket closed


    //get ticket content

    //get tickets contents


}
