package com.jinhongs.eternity.view.web.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义认证令牌 作为技术学习储备，暂未使用
 */
public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;
    private String oauthToken;
    public CustomAuthenticationToken(String oauthToken) {
        super(null);
        this.principal = oauthToken;
        this.credentials = oauthToken;
        setAuthenticated(false);
        this.oauthToken = oauthToken;
    }

    public CustomAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return oauthToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public String getName() {
        return oauthToken;
    }
}
