package com.mydomomain.silverpay.controller.site.panel;

import com.mydomomain.silverpay.configuration.model_mapper.UserMapper;
import com.mydomomain.silverpay.dto.site.panel.users.UserDetailDto;
import com.mydomomain.silverpay.dto.site.panel.users.UserLoginDto;
import com.mydomomain.silverpay.dto.site.panel.users.UserRegisterDto;
import com.mydomomain.silverpay.helper.Jwt;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.model.ReturnMessage;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import com.mydomomain.silverpay.service.userService.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;

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
    public ResponseEntity<Object> register(@RequestBody @Valid UserRegisterDto userRegisterDto

            , HttpServletRequest request) throws FileNotFoundException {

//
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

        Photo photo = new Photo();
        photo.setUser(user);
        photo.setDescription("profile pic");
        photo.setAlt("profile pic");
        photo.setIsMain(true);
        photo.setPublicId("0");
        photo.setPhotoUrl(String.format("%s://%s:%s/%s", request.getScheme(), request.getServerName(), request.getServerPort(), "/content/pic/default_profile.png"));//create web like path

        User u = authService.register(user, photo, userRegisterDto.getPassword());

        return new ResponseEntity<>(u, HttpStatus.CREATED);


    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) {

        User user = authService.login(userLoginDto.getUsername(), userLoginDto.getPassword());

        if (user == null) {
            ReturnMessage returnMessage = new ReturnMessage(false, "invalid username or password", "error");
            return new ResponseEntity<>(returnMessage, HttpStatus.UNAUTHORIZED);
        }

        String token = jwt.jwtGeneration(userLoginDto.getUsername(), user.getId(), true);

        UserDetailDto userDetailDto = UserMapper.instance.detailDto(user);

        return new ResponseEntity<>(Map.of("token", token, "user", user), HttpStatus.OK);

    }


}
