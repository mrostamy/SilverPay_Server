package com.mydomomain.silverpay.controller.site.panel;

import com.mydomomain.silverpay.Routes.Routes;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Routes.User.url)
public class UserController {

    IUserRepository userRepository;

    UserController(IUserRepository repository) {
        this.userRepository = repository;
    }

    @GetMapping(Routes.User.users)
    public ResponseEntity<Object> getAllUsers() {

        List<User> users = userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);

    }


}
