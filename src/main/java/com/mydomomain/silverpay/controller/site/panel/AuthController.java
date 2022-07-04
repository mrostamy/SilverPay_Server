package com.mydomomain.silverpay.controller.site.panel;

import com.mydomomain.silverpay.dto.site.panel.users.UserLoginDto;
import com.mydomomain.silverpay.dto.site.panel.users.UserRegisterDto;
import com.mydomomain.silverpay.helper.Jwt;
import com.mydomomain.silverpay.model.ReturnMessage;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import com.mydomomain.silverpay.service.userService.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/site/panel/auth")
public class AuthController {


    AuthService authService;

    IUserRepository userRepository;

    Jwt jwt;

    AuthController(AuthService authService, IUserRepository userRepository, Jwt jwt) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwt = jwt;
    }


    //    @PostMapping(Routes.Auth.register)
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {

//
        if (userRepository.existsUserByUsername(userRegisterDto.getUsername())) {

            ReturnMessage message = new ReturnMessage(false, "username exist", "error");

            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        }

        User user = new User();
        user.setAddress("");
        user.setCity("tehran");
        user.setDateOfBirth(LocalDateTime.now().toString());
        user.setName(userRegisterDto.getName());
        user.setPhoneNumber(userRegisterDto.getPhoneNumber());
        user.setUsername(userRegisterDto.getUsername().toLowerCase());

        User u = authService.register(user, userRegisterDto.getPassword());

        return new ResponseEntity<>(u, HttpStatus.CREATED);




    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) {

//        throw new RuntimeException("ggggg");

        User user = authService.login(userLoginDto.getUsername(), userLoginDto.getPassword());

        if (user == null) {
            System.out.println("user is null");
            ReturnMessage returnMessage = new ReturnMessage(false, "invalid username or password", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }


        String token = jwt.jwtGeneration(userLoginDto.getUsername(), "h", true);

        return new ResponseEntity<>(token, HttpStatus.OK);

    }


}
