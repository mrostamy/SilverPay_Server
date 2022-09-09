package com.mydomain.silverpay.controller.app.V1.panel.admin;

import com.mydomain.silverpay.Routes.V1.Routes;
import com.mydomain.silverpay.configuration.model_mapper.UserMapper;
import com.mydomain.silverpay.data.dto.app.panel.PaginationDto;
import com.mydomain.silverpay.data.dto.app.panel.role.RoleEditDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.UserDetailDto;
import com.mydomain.silverpay.repository.MainDB.IUserRepository;
import com.mydomain.silverpay.data.model.mainDB.Role;
import com.mydomain.silverpay.data.model.mainDB.User;
import com.mydomain.silverpay.service.userService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("admin_user")
public class UserController {

    final IUserRepository userRepository;

    final UserService userService;

    UserController(IUserRepository userRepository, UserService userService) {

        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(Routes.AdminUser.users)
//    @PreAuthorize("hasAnyAuthority('ROLE_Admin,ROLE_User')")
    public ResponseEntity<Object> getUsers(PaginationDto paginationDto) {

        //add search and pagination

        // List<Object[]> users = userRepository.findUsernameAndIdAndRoles();



//        List<UserView> users = userRepository.findAllProjectedBy();
        List<User> users = userRepository.findAll();

        List<UserDetailDto> userDetails= UserMapper.instance.userReturnDto(users);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping(Routes.AdminUser.editRoles)
    public ResponseEntity<?> editRoles(@PathVariable String username, RoleEditDto roleEditDto) {

        User user = userRepository.findByUsername(username).orElse(null);

        List<Role> initRoles = new ArrayList<>();

        String[] selectedRoles = roleEditDto.getRoleNames();

        if (selectedRoles == null) {
            selectedRoles = new String[]{};
        }

        for (String roleName : selectedRoles) {

            initRoles.add(new Role(roleName));
        }


        if (user != null) {
            user.setRoles(initRoles);

            userRepository.save(user);

            return new ResponseEntity<>(user, HttpStatus.OK);

        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

    }




}
