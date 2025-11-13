package com.jinhongs.eternity.admin.web.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.Optional;

/**
 * 自定义认证过滤器 不同的基类可以实现不同的作用(作为技术学习储备，暂未使用) 比如OncePerRequestFilter可以作为接口请求中对cookie的验证
 * GenericFilterBean	Spring 中所有过滤器的通用基类，提供生命周期管理和参数配置功能。
 * OncePerRequestFilter	确保每个请求只执行一次过滤逻辑（来自 Spring 框架，非 Spring Security 特有）。	LogoutFilter、CorsFilter、自定义日志过滤器
 * AbstractAuthenticationProcessingFilter 处理用户主动发起的认证请求。（如登录表单、OAuth2 登录）的模板基类，规范认证流程。
 * AbstractPreAuthenticatedProcessingFilter	处理预认证场景（信任外部系统提供的已认证用户信息）（如集成 LDAP 或 Kerberos）。
 */
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    protected CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("attemptAuthentication");
        String oauthToken = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(c -> "oauthToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        return super.getAuthenticationManager().authenticate(
                new CustomAuthenticationToken(oauthToken)
        );
    }
}
