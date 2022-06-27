package com.mydomomain.silverpay.service.userService;

import com.mydomomain.silverpay.helper.PasswordHash;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final IUserRepository userRepo;

    public AuthService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User register(User user, String password) {

        String pass = PasswordHash.passwordHash(password);
        user.setPassword(pass);

        return userRepo.save(user);

    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username).get();

        System.out.println("password: " + user.getPassword());

        if (PasswordHash.verifyPassword(user.getPassword(), password)) {

            return user;

        } else {
            return null;
        }
    }
}



