package com.bank.authorization.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication
    ) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("ROLE_ADMIN");
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/role");
        } else if (roles.contains("ROLE_USER")) {
            System.out.println("ROLE_USER");
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/role");
        } else {
            System.out.println("ROLE_role");
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/role");
        }
    }
}