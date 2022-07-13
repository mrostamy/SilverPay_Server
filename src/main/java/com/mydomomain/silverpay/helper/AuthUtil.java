package com.mydomomain.silverpay.helper;

import com.mydomomain.silverpay.model.User;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class AuthUtil {

    public boolean authCheck(User user, Principal principal) {

        if (user == null || principal==null) {
            return true;

        }

        return !user.getUsername().equalsIgnoreCase(principal.getName());
    }
}
