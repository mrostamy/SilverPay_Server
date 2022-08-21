package com.mydomomain.silverpay.controller.site.V1.auth;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.dto.site.panel.users.UserLoginDto;
import com.mydomomain.silverpay.dto.site.panel.users.UserRegisterDto;
import com.mydomomain.silverpay.helper.JwtUtil;
import com.mydomomain.silverpay.model.*;
import com.mydomomain.silverpay.repository.mainRepository.IUserRepository;
import com.mydomomain.silverpay.service.userService.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class AuthController {

    final HttpServletRequest request;

    AuthService authService;

    IUserRepository userRepository;

    JwtUtil jwtUtil;

    AuthController(AuthService authService, IUserRepository userRepository, JwtUtil jwtUtil, HttpServletRequest request) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.request = request;
    }

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    //    @PostMapping(Routes.Auth.register)
    @PostMapping(Routes.Auth.register)
//    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public ResponseEntity<Object> register(@RequestBody @Valid UserRegisterDto userRegisterDto

            , HttpServletRequest request) throws FileNotFoundException {

//
        logger.info("ip: " + request.getRemoteAddr());

        if (userRepository.existsUserByUsername(userRegisterDto.getUsername())) {

            ReturnMessage message = new ReturnMessage(false, "username exist", "error");

            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        }


        User user = new User();
        user.setAddress("");
        user.setCity("tehran");
        user.setIsActive(true);
        user.setLastActive("now");
        user.setStatus(true);
        user.setGender("male");
        user.setDateOfBirth(LocalDateTime.now().toString());
        user.setName(userRegisterDto.getName());
        user.setPhoneNumber(userRegisterDto.getPhoneNumber());
        user.setUsername(userRegisterDto.getUsername().toLowerCase());


        Notification notification = new Notification();

        notification.setUser(user);
        notification.setEnterEmail(true);
        notification.setEnterSms(false);
        notification.setEnterTelegram(true);
        notification.setExitEmail(true);
        notification.setExitSms(false);
        notification.setExitTelegram(true);
        notification.setTicketEmail(true);
        notification.setTicketSms(false);
        notification.setTicketTelegram(true);
        notification.setLoginEmail(true);
        notification.setLoginSms(false);
        notification.setLoginTelegram(true);

        Wallet walletMain = new Wallet();

        walletMain.setName("main silverpay");
        walletMain.setMain(true);
        walletMain.setSms(false);
        walletMain.setUser(user);

        Wallet walletSms = new Wallet();

        walletSms.setName("sms silverpay");
        walletSms.setMain(true);
        walletSms.setSms(false);
        walletSms.setUser(user);

        Photo photo = new Photo();
        photo.setUser(user);
        photo.setDescription("profile pic");
        photo.setAlt("profile pic");
        photo.setIsMain(true);
        photo.setPublicId("0");
        photo.setPhotoUrl(String.format("%s://%s:%s/%s", request.getScheme(), request.getServerName(), request.getServerPort(), "/content/pic/default_profile.png"));//create web like path

        User u = authService.register(user, notification, photo,walletMain,walletSms ,userRegisterDto.getPassword());

        return new ResponseEntity<>(u, HttpStatus.CREATED);


    }

    @PostMapping(Routes.Auth.login)
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) {

        logger.info("ip: " + request.getRemoteAddr());

        User user = authService.login(userLoginDto.getUsername(), userLoginDto.getPassword());

        if (user == null) {
            ReturnMessage returnMessage = new ReturnMessage(false, "invalid username or password", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }

//        String token = jwtUtil.CreateAccessToken(user, true);

        //UserDetailDto userDetailDto = UserMapper.instance.detailDto(user);

        return new ResponseEntity<>(Map.of("token", "token", "user", user), HttpStatus.OK);

    }


}
