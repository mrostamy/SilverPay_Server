package com.mydomomain.silverpay.controller.site.panel;

import com.mydomomain.silverpay.Routes.Routes;
import com.mydomomain.silverpay.configuration.model_mapper.UserMapping;
import com.mydomomain.silverpay.dto.site.panel.users.UserDetailDto;
import com.mydomomain.silverpay.dto.site.panel.users.UserListDto;
import com.mydomomain.silverpay.helper.AuthUtil;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import io.jsonwebtoken.Jwt;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Routes.User.url)
public class UserController {

    IUserRepository userRepository;

    final ModelMapper modelMapper;

    UserController(IUserRepository repository, ModelMapper modelMapper) {
        this.userRepository = repository;
        this.modelMapper = modelMapper;
    }

    UserMapping userMapper = Mappers.getMapper(UserMapping.class);

    @GetMapping
    public ResponseEntity<Object> getUsers() {

        List<User> users = userRepository.findAll();

        List<UserListDto> userDtos = new ArrayList<>();

        users.forEach(user -> userDtos.add(modelMapper.map(user, UserListDto.class)));

        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id, Principal principal) {

        User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;

        if(!new AuthUtil().authCheck(user,principal)){
            return new ResponseEntity<>("UnAuthorized Access Detected", HttpStatus.UNAUTHORIZED);

        }

        UserMapping userMapping = Mappers.getMapper(UserMapping.class);

        UserDetailDto userDetailDto = userMapping.detailDto(user);

        return new ResponseEntity<>(userDetailDto, HttpStatus.OK);

    }
}



