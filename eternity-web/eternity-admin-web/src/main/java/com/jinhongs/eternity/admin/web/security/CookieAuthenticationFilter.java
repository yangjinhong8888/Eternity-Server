package com.jinhongs.eternity.admin.web.security;

import com.jinhongs.eternity.common.constant.RedisConstants;
import com.jinhongs.eternity.dao.redis.client.RedisClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * 自定义认证过滤器，作为接口请求对cookie的验证
 * OncePerRequestFilter	确保每个请求只执行一次过滤逻辑，这里用来作为接口请求对cookie的验证
 */
public class CookieAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisClient redisClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("cookie验证");
        String token = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(c -> "token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        Authentication authentication = (Authentication) redisClient.get(RedisConstants.getBaseSessionAdmin(token));
        // 如果没有登录这里是null，则会触发Security 401
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
