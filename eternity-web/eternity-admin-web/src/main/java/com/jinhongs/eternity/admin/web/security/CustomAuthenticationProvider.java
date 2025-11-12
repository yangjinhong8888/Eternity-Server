package com.jinhongs.eternity.admin.web.security;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 自定义认证提供者 (作为技术学习储备，暂未使用)
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * 认证方法
     *
     * @param authentication 认证信息
     * @return 认证结果 xxAuthenticationToken
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    /**
     * 判断是否支持该认证方式
     *
     * @param authentication 认证信息
     * @return true/false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
