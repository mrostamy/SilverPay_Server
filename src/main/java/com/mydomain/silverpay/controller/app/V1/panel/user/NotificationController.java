package com.mydomain.silverpay.controller.app.V1.panel.user;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.NotificationMapper;
import com.mydomain.silverpay.data.dto.app.panel.notification.NotificationCountDto;
import com.mydomain.silverpay.data.dto.app.panel.notification.NotificationUpdateDto;
import com.mydomain.silverpay.data.model.mainDB.Notification;
import com.mydomain.silverpay.repository.FinancialDB.IEntryRepository;
import com.mydomain.silverpay.repository.MainDB.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class NotificationController {


    final INotificationRepository notifyRepo;

    @Autowired
    ITicketRepository ticketRepository;

    @Autowired
    IBlogRepository blogRepository;

    @Autowired
    IDocumentRepository documentRepository;

    @Autowired
    IBankCardRepository bankCardRepository;


    @Autowired
    IGateRepository gateRepository;

    @Autowired
    IEntryRepository entryRepository;

    public NotificationController(INotificationRepository notifyRepo) {
        this.notifyRepo = notifyRepo;
    }

    @GetMapping(Routes.Notification.getUserNotify)
    public ResponseEntity<?> userNotification(
            Principal principal,
            @PathVariable String user_id
            , @PathVariable String notify_id) {


        Notification notification = notifyRepo.findById(notify_id).orElse(null);

        if (notification != null) {

            if (!principal.getName().equalsIgnoreCase(user_id)) {
                return new ResponseEntity<>("UnAuthorize Access detected", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(notification, HttpStatus.OK);


        } else {
            return new ResponseEntity<>("no notification found", HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping(Routes.Notification.getUserNotifies)
    public ResponseEntity<?> userNotifications(
            @PathVariable String user_id,
            HttpServletRequest request) {
        NotificationCountDto res = new NotificationCountDto();


        if (request.isUserInRole("Admin")) {

            res.setUnClosedTicketCount(ticketRepository.countByClosed(false));
            res.setUnVerifiedDocumentsCount(documentRepository.countByApprove((short) 0));

//            gateRepository.count(Example.of());


            res.setUnCheckedEntry(entryRepository.countByApprove(false));
            res.setUnSpecifiedEntry(entryRepository.countByApproveAndRejectAndPayed(true, false, false));

            res.setUnVerifiedBankCardsInPastSevenDays(bankCardRepository.countByApproveAndModifiedAtBefore(false,
                    LocalDateTime.now().plusDays(7)
            ));
            res.setUnVerifiedGateInPastSevenDays(gateRepository.countByActiveAndModifiedAtBefore(false,
                    LocalDateTime.now().plusDays(7)
            ));

            res.setUnVerifiedBlogCount(blogRepository.countByStatus(false));


        }
        if (request.isUserInRole("Accountant")) {

            res.setUnCheckedEntry(entryRepository.countByApprove(false));
            res.setUnSpecifiedEntry(entryRepository.countByApproveAndRejectAndPayed(true, false, false));

            res.setUnVerifiedBankCardsInPastSevenDays(bankCardRepository.countByApproveAndModifiedAtBefore(false,
                    LocalDateTime.now().plusDays(7)
            ));
            res.setUnVerifiedGateInPastSevenDays(gateRepository.countByActiveAndModifiedAtBefore(false,
                    LocalDateTime.now().plusDays(7)
            ));

        }

        if (request.isUserInRole("AdminBlog")) {

            res.setUnVerifiedBlogCount(blogRepository.countByStatus(false));

        }

        if (request.isUserInRole("User")) {

            res.setUnClosedTicketCount(ticketRepository.countByUser_IdAndClosed(user_id, false));
        }

        return ok(res);

    }

    @PutMapping(Routes.Notification.updateNotify)
    public ResponseEntity<?> updateNotification(
            Principal principal,
            @PathVariable String user_id
            , NotificationUpdateDto notificationUpdateDto) {

        if (!principal.getName().equalsIgnoreCase(user_id)) {
            return new ResponseEntity<>("UnAuthorize Access detected", HttpStatus.UNAUTHORIZED);
        }

        Notification notification = notifyRepo.findAll()
                .stream().filter(notify -> Objects.equals(notify.getUser().getId(), user_id))
                .findFirst().orElse(null);

        if (notification != null) {
            var notificationUpdate = NotificationMapper.instance.notificationUpdateMapper(notificationUpdateDto);

            var result = notifyRepo.save(notificationUpdate);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            Notification newNotification = new Notification();

            newNotification.getUser().setId(user_id);

            newNotification = NotificationMapper.instance.notificationUpdateMapper(notificationUpdateDto);

            Notification notification1 = notifyRepo.save(newNotification);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

    }


}
