package com.mydomomain.silverpay.configuration.security;

import com.mydomomain.silverpay.helper.JwtUtil;
import com.mydomomain.silverpay.model.Role;
import com.mydomomain.silverpay.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {


    final JwtUtil jwtUtil;

    public JwtTokenFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("has token : *********"+hasAuthorizationBearer(request));

        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);

        System.out.println("validate token:************  "+jwtUtil.validateToken(token));

        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);


    }


    private boolean hasAuthorizationBearer(HttpServletRequest request) {


        String header = request.getHeader("Authorization");

        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {

            return false;
        }

        return true;


    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();

    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {

        UserDetails userDetails = getUserDetails(token);


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }

    private UserDetails getUserDetails(String token) {

        User userDetails = new User();
        Claims claims = jwtUtil.parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");
        roles = roles.replace("[", "").replace("]", "");

        System.out.println("roles: "+roles);
        String[] roleNames = roles.split(",");

        for (String aRoleName : roleNames) {
            System.out.println("Role name:++++"+aRoleName);
            userDetails.addRole(new Role(aRoleName.trim()));
        }

        String[] jwtSubject = subject.split(",");
        userDetails.setId(jwtSubject[1]);
        userDetails.setUsername(jwtSubject[0]);

        return userDetails;
    }
}


