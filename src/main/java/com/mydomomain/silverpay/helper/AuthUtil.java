package com.mydomomain.silverpay.helper;

import com.mydomomain.silverpay.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class AuthUtil {

    public boolean authCheck(User user, Principal principal) {

        if (user == null) {
            return false;

        }

        return user.getUsername().equalsIgnoreCase(principal.getName());
    }
}
