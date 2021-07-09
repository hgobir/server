package com.fdm.server.project.server;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter method in AuthenticationFilter hit!!");
        System.out.println("\nthis is what request server name looks like !" + servletRequest.getServerName());
        System.out.println("this is what request server port looks like !" + servletRequest.getServerPort() + "\n");

//        System.out.println("\nthis is what request server name looks like !" + servletResponse.);
//        System.out.println("this is what request server port looks like !" + servletResponse.getServerPort() + "\n");
        Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) servletRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
