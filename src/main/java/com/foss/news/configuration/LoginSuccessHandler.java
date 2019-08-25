package com.foss.news.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().setAttribute("auth",authentication);
        request.getSession().removeAttribute("login_error");
        List<GrantedAuthority> roles= (List<GrantedAuthority>) authentication.getAuthorities();
        if(roles.toString().contains("ADMIN")){
            response.sendRedirect("/admin");
        }else {
            response.sendRedirect("/");
        }
    }
}
