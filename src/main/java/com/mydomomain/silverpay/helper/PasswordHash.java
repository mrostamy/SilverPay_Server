package com.mydomomain.silverpay.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHash {

    public static String passwordHash(String password) {

        return new BCryptPasswordEncoder().encode(password);

    }

    public static boolean verifyPassword(String userPassword, String password) {

        return new BCryptPasswordEncoder().matches(password, userPassword);
    }

}
