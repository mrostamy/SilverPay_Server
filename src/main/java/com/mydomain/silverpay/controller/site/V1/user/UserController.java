package com.mydomain.silverpay.controller.site.V1.user;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.UserMapper;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.PasswordResetDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.UserDetailDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.UserListDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.UserUpdateDto;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.service.userService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    final IUserRepository userRepository;

    final UserService userService;

    UserController(IUserRepository userRepository, UserService userService) {

        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping(Routes.User.users)
    public ResponseEntity<Object> getUsers() {

        List<User> users = userRepository.findAll();

        List<UserListDto> userDtos = new ArrayList<>();

        users.forEach(user -> userDtos.add(UserMapper.instance.userListDto(user)));

        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @GetMapping(Routes.User.user)
    public ResponseEntity<Object> getUser(@PathVariable String id, Principal principal) throws FileNotFoundException {

        if (!principal.getName().equals(id)) {
            return new ResponseEntity<>("UnAuthorize Access Detected", HttpStatus.UNAUTHORIZED);
        }


        User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;


        UserDetailDto userDetailDto = UserMapper.instance.detailDto(user);

//        UserDetailDto userDetailDto=modelMapper.map(user,UserDetailDto.class);

        return new ResponseEntity<>(userDetailDto, HttpStatus.OK);

    }

    @PostMapping(Routes.User.insert)
    public ResponseEntity<Object> insertUser(@RequestBody User user, Principal principal) {

        if (!user.getId().equals(principal.getName())) {

            return new ResponseEntity<>("UnAuthorize Access Detected", HttpStatus.UNAUTHORIZED);
        }
        User ur = userRepository.save(user);
        return new ResponseEntity<>(ur, HttpStatus.OK);


    }

    @PutMapping(Routes.User.update)
    public ResponseEntity<Object> updateUser(@PathVariable String id
            , @RequestBody @Valid UserUpdateDto userUpdateDto, Principal principal) {

        if (!principal.getName().equals(id)) {

            return new ResponseEntity<>("UnAuthorize Access Detected", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.getReferenceById(id);

        user = UserMapper.instance.user(userUpdateDto);

        System.out.println("name: " + user.getName());

//        user.setPassword("1234");
//        user.setUsername("mmmm");
//        user.setLastActive("now");


        userRepository.update(user);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping(Routes.User.reset_password)
    public ResponseEntity<Object> resetPassword(@PathVariable String id
            , @RequestBody @Valid PasswordResetDto passwordResetDto) {

        User user = userService.checkUserPassword(id, passwordResetDto.getOldPassword());

        if (user == null) {
            return new ResponseEntity<>("old password in incorrect", HttpStatus.BAD_REQUEST);

        }

        if (userService.updatePassword(user, passwordResetDto.getNewPassword())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("error in update password", HttpStatus.BAD_REQUEST);
        }

    }
}



