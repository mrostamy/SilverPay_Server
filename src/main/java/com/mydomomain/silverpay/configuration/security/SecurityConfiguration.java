package com.mydomomain.silverpay.configuration.security;

import com.mydomomain.silverpay.repository.main.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    final IUserRepository userRepository;

    final JwtTokenFilter jwtTokenFilter;

    public SecurityConfiguration(IUserRepository userRepository, JwtTokenFilter jwtTokenFilter) {
        this.userRepository = userRepository;
        this.jwtTokenFilter = jwtTokenFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
        http.authorizeRequests()
                .antMatchers("/**/login").permitAll()
                .antMatchers("/**/register").permitAll()
                .antMatchers("/api-doc/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
        ;
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(((request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())
        ));
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(
                username -> userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("user with username " + username +
                                        " not found")));

    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
