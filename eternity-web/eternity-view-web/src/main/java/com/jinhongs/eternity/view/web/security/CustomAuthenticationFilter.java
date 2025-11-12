package com.jinhongs.eternity.view.web.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

/**
 * 自定义认证过滤器(作为技术学习储备，暂未使用)
 */
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    protected CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        logger.info("attemptAuthentication");
        String oauthToken = request.getParameter("oauthtoken");
        return super.getAuthenticationManager().authenticate(
                new CustomAuthenticationToken(oauthToken)
        );
    }
}
