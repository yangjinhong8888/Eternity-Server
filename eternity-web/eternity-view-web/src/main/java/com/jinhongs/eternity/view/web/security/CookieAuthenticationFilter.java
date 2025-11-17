package com.jinhongs.eternity.view.web.security;

import com.jinhongs.eternity.common.constant.RedisConstants;
import com.jinhongs.eternity.dao.redis.client.RedisClient;
import com.jinhongs.eternity.service.model.dto.security.SecurityUserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@Slf4j
public class CookieAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisClient redisClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("token验证");
        String token = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(c -> "token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token == null) {
            log.info("请求不包含token");
            // 没有获得cookie，证明登录过期或者第一次访问，直接放行给SpringSecurity处理
            filterChain.doFilter(request, response);
            return;
        }
        SecurityUserDetailsImpl securityUserDetails = (SecurityUserDetailsImpl) redisClient.get(RedisConstants.getViewSessionToken(token));

        if (securityUserDetails == null) {
            log.info("登录已过期");
            // 如果有cookie，但是查不到用户信息，则证明登录过期，或者伪造cookie登录，放行给SpringSecurity处理
            filterChain.doFilter(request, response);
            return;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                securityUserDetails,
                null,
                securityUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
