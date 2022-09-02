package com.mydomain.silverpay.service.userService;

import com.mydomain.silverpay.helper.PasswordHash;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User checkUserPassword(String id, String oldPassword) {

        User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;

        if (!PasswordHash.verifyPassword(user.getPassword(), oldPassword)) {

            return null;
        }

        return user;

    }

    public boolean updatePassword(User user, String newPassword) {


        user.setPassword(newPassword);

        userRepository.save(user);

        return true;

    }
}
