package com.mydomomain.silverpay.configuration.security;

import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.repository.main.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository repository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username).get();
    }
}
