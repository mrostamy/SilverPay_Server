package com.mydomomain.silverpay.service.userService;

import com.mydomomain.silverpay.helper.PasswordHash;
import com.mydomomain.silverpay.model.Notification;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.INotificationRepository;
import com.mydomomain.silverpay.repository.main.IPhotoRepository;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final IUserRepository userRepo;
    final IPhotoRepository photoRepo;

    final INotificationRepository notificationRepo;

    public AuthService(IUserRepository userRepo, IPhotoRepository photoRepo, INotificationRepository notificationRepo) {
        this.userRepo = userRepo;
        this.photoRepo = photoRepo;
        this.notificationRepo = notificationRepo;
    }

    public User register(User user, Notification notification, Photo photo, String password) {

        String pass = PasswordHash.passwordHash(password);
        user.setPassword(pass);



        userRepo.save(user);
        photoRepo.save(photo);
        notificationRepo.save(notification);

        return user;
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username).orElse(null);

        if (PasswordHash.verifyPassword(user.getPassword(), password)) {

            return user;

        } else {
            return null;
        }
    }
}



