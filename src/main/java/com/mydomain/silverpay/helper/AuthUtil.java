package com.mydomain.silverpay.helper;

import com.mydomain.silverpay.data.model.mainDB.User;
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
