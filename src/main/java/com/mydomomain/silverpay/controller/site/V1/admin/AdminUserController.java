package com.mydomomain.silverpay.controller.site.V1.admin;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.dto.site.panel.role.RoleEditDto;
import com.mydomomain.silverpay.model.Role;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import com.mydomomain.silverpay.repository.main.UserView;
import com.mydomomain.silverpay.service.userService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class AdminUserController {

    final IUserRepository userRepository;

    final UserService userService;

    AdminUserController(IUserRepository userRepository, UserService userService) {

        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(Routes.AdminUser.users)
//    @PreAuthorize("hasAnyAuthority('ROLE_Admin,ROLE_User')")
    public ResponseEntity<Object> getUsers() {

        // List<Object[]> users = userRepository.findUsernameAndIdAndRoles();

        List<UserView> users = userRepository.findAllProjectedBy();
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
