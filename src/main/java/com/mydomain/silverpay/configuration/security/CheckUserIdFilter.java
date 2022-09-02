package com.mydomain.silverpay.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.security.Principal;

@WebFilter(filterName = "filter" ,urlPatterns = "/**")
public class CheckUserIdFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Principal principal = (Principal) authentication.getDetails();

        System.out.println("check name" + principal.getName());

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
