package com.mydomain.silverpay.controller.app.V1.panel.auth;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.data.dto.app.panel.auth.GetVerificationCodeDto;
import com.mydomain.silverpay.data.model.ReturnMessage;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.UserLoginDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.UserRegisterDto;
import com.mydomain.silverpay.helper.JwtUtil;
import com.mydomain.silverpay.data.model.mainDB.Notification;
import com.mydomain.silverpay.data.model.mainDB.Photo;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.service.userService.AuthService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "register user"
                    , content = {@Content(mediaType = "application/json"
                    , schema = @Schema(implementation = UserRegisterDto.class))}),
            @ApiResponse(responseCode = "401", description = "unAuthorized access")
    })


    public ResponseEntity<Object> register(@RequestBody @Valid UserRegisterDto userRegisterDto

            , HttpServletRequest request) throws FileNotFoundException {

//
        logger.info("ip: " + request.getRemoteAddr());

//        if (userRepository.existsUserByUsername(userRegisterDto.getPhoneNumber())) {
//
//            ReturnMessage message = new ReturnMessage(false, "username exist", "error");
//
//            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//
//        }













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
        user.setUsername(userRegisterDto.getPhoneNumber());


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

        User u = authService.register(user, notification, photo, walletMain, walletSms, userRegisterDto.getPassword());

        return new ResponseEntity<>(u, HttpStatus.CREATED);


    }

    @PostMapping(Routes.Auth.login)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login user"
                    , content = {@Content(mediaType = "application/json"
                    , schema = @Schema(implementation = UserLoginDto.class))}),
            @ApiResponse(responseCode = "404", description = "invalid username or password")
    })
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

    @PostMapping(Routes.Auth.get_verification_code)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login user"
                    , content = {@Content(mediaType = "application/json"
                    , schema = @Schema(implementation = UserLoginDto.class))}),
            @ApiResponse(responseCode = "404", description = "invalid username or password")
    })


    public ResponseEntity<Object> getVerificationCode(@RequestBody @Valid GetVerificationCodeDto CodeDto) {






        return null;

    }





}
