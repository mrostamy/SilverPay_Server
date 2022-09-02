package com.mydomain.silverpay.service.userService;

import com.mydomain.silverpay.helper.PasswordHash;
import com.mydomain.silverpay.data.model.mainDB.Notification;
import com.mydomain.silverpay.data.model.mainDB.Photo;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.MainDB.INotificationRepository;
import com.mydomain.silverpay.repository.MainDB.IPhotoRepository;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final IUserRepository userRepo;
    final IPhotoRepository photoRepo;
    final IWalletRepository walletRepository;
    final INotificationRepository notificationRepo;

    public AuthService(IUserRepository userRepo, IPhotoRepository photoRepo, INotificationRepository notificationRepo, IWalletRepository walletRepository) {
        this.userRepo = userRepo;
        this.photoRepo = photoRepo;
        this.notificationRepo = notificationRepo;
        this.walletRepository = walletRepository;
    }

    public User register(User user, Notification notification,
                         Photo photo, Wallet walletMain,Wallet walletSms, String password) {

        String pass = PasswordHash.passwordHash(password);
        user.setPassword(pass);



        userRepo.save(user);
        photoRepo.save(photo);
        notificationRepo.save(notification);
        walletRepository.save(walletMain);
        walletRepository.save(walletSms);

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



