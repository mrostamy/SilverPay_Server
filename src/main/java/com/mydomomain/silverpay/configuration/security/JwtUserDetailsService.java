package com.mydomomain.silverpay.configuration.security;

import com.mydomomain.silverpay.repository.mainRepository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository repository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("" +
                "user not found with username" + username));
    }

}
