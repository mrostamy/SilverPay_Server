package com.mydomomain.silverpay.service.userService;

import com.mydomomain.silverpay.helper.PasswordHash;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.mainRepository.IUserRepository;
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
