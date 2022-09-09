package com.mydomain.silverpay.controller.app.V1.panel.admin;

import com.mydomain.silverpay.data.dto.app.panel.role.RoleEditDto;
import com.mydomain.silverpay.data.dto.app.panel.role.RolesReturnDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController("admin_roleController")
public class RolesController {


    @PutMapping("/adminRoles/edit")
    public ResponseEntity<?> getRoles(RoleEditDto editRoleDto, Authentication authentication) {

        var authorities = authentication.getAuthorities();

        List<RolesReturnDto> roles = new ArrayList<>();

        for (GrantedAuthority authority : authorities
        ) {
            if (authority.getAuthority().equals("Role_Admin"))
                roles.add(new RolesReturnDto("admin", true));
            else
                roles.add(new RolesReturnDto("admin", false));

            if (authority.getAuthority().equals("Role_Accountant"))
                roles.add(new RolesReturnDto("accountant", true));
            else
                roles.add(new RolesReturnDto("accountant", false));


            if (authority.getAuthority().equals("Role_Blog"))
                roles.add(new RolesReturnDto("blog", true));
            else
                roles.add(new RolesReturnDto("blog", false));


            if (authority.getAuthority().equals("Role_AdminBlog"))
                roles.add(new RolesReturnDto("adminBlog", true));
            else
                roles.add(new RolesReturnDto("adminBlog", false));

            if (authority.getAuthority().equals("Role_User"))
                roles.add(new RolesReturnDto("user", true));
            else
                roles.add(new RolesReturnDto("user", false));


        }
        return ok(roles);

    }

    @PutMapping("/adminRoles/changeRoles")
    public ResponseEntity<?> changeRoles(String userId, RoleEditDto editRoleDto) {

        //find user

        //fetch user roles

        //

        return noContent().build();

    }


}
