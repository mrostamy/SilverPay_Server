package com.mydomomain.silverpay.service.userService;

import com.mydomomain.silverpay.helper.PasswordHash;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IPhotoRepository;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final IUserRepository userRepo;
    final IPhotoRepository photoRepo;

    public AuthService(IUserRepository userRepo, IPhotoRepository photoRepo) {
        this.userRepo = userRepo;
        this.photoRepo = photoRepo;
    }

    public User register(User user, Photo photo, String password) {

        String pass = PasswordHash.passwordHash(password);
        user.setPassword(pass);


        userRepo.save(user);
        photoRepo.save(photo);

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



