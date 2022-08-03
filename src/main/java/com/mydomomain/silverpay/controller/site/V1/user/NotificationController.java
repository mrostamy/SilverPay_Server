package com.mydomomain.silverpay.controller.site.V1.user;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.NotificationMapper;
import com.mydomomain.silverpay.dto.site.panel.notification.NotificationUpdateDto;
import com.mydomomain.silverpay.model.Notification;
import com.mydomomain.silverpay.repository.main.INotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

@RestController
public class NotificationController {


    final INotificationRepository notifyRepo;

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
