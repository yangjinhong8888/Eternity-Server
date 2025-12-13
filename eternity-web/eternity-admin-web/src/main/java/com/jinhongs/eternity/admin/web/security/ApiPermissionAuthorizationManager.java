package com.jinhongs.eternity.admin.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinhongs.eternity.common.constant.RedisConstants;
import com.jinhongs.eternity.dao.mysql.repository.ApiPermissionRepository;
import com.jinhongs.eternity.dao.redis.client.RedisClient;
import com.jinhongs.eternity.model.entity.ApiPermission;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 基于数据库接口映射的权限校验器：
 * 1) 从表 api_permission 读取 path/method 对应的 permKey（缓存 60 秒）；
 * 2) 命中映射则校验用户是否拥有该 permKey；未命中映射则默认放行（只要求已登录）；
 * 3) 未登录交给 AuthenticationEntryPoint 处理，权限不足抛 AccessDeniedException 走 AccessDeniedHandler。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiPermissionAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final String PERMISSION_CACHE_KEY = RedisConstants.BASE_ADMIN_METHOD_PERMISSION_CACHE;
    private static final long LOCAL_CACHE_TTL_MILLIS = 60_000L;
    private static final long REDIS_CACHE_TTL_SECONDS = 600L;

    private final ApiPermissionRepository apiPermissionRepository;
    private final RedisClient redisClient;
    private final ObjectMapper objectMapper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private volatile List<ApiPermission> cachedRules = Collections.emptyList();
    private volatile long lastLoadedAt = 0L;

    /**
     * 依据当前请求的 path/method 匹配数据库规则并校验权限
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return new AuthorizationDecision(true);
        }
        Authentication auth = authentication.get();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return new AuthorizationDecision(false);
        }
        ApiPermission rule = matchRule(loadRules(), request);
        if (rule == null || !StringUtils.hasText(rule.getPermKey())) {
            return new AuthorizationDecision(true);
        }
        boolean granted = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(rule.getPermKey()));
        if (!granted) {
            log.warn("权限不足，请求 {} {} 需要权限 {}", request.getMethod(), request.getRequestURI(), rule.getPermKey());
            throw new AccessDeniedException("权限不足");
        }
        return new AuthorizationDecision(true);
    }

    /**
     * 从缓存规则中匹配当前请求
     */
    private ApiPermission matchRule(List<ApiPermission> rules, HttpServletRequest request) {
        return rules.stream()
                .filter(rule -> pathMatcher.match(rule.getPath(), request.getRequestURI()))
                .filter(rule -> !StringUtils.hasText(rule.getHttpMethod()) || Objects.equals(rule.getHttpMethod(), request.getMethod()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 读取权限映射，优先本地缓存，其次Redis，最后数据库
     */
    private List<ApiPermission> loadRules() {
        long now = System.currentTimeMillis();
        if (now - lastLoadedAt < LOCAL_CACHE_TTL_MILLIS) {
            return cachedRules;
        }
        synchronized (this) {
            if (now - lastLoadedAt < LOCAL_CACHE_TTL_MILLIS) {
                return cachedRules;
            }
            List<ApiPermission> redisRules = readFromRedis();
            if (!redisRules.isEmpty()) {
                cachedRules = redisRules;
                lastLoadedAt = now;
                return cachedRules;
            }
            cachedRules = apiPermissionRepository.list();
            lastLoadedAt = now;
            cacheToRedis(cachedRules);
            return cachedRules;
        }
    }

    private List<ApiPermission> readFromRedis() {
        Object cached = redisClient.get(PERMISSION_CACHE_KEY);
        if (!(cached instanceof List<?> list) || list.isEmpty()) {
            return Collections.emptyList();
        }
        Object first = list.get(0);
        if (first instanceof ApiPermission) {
            return list.stream()
                    .filter(ApiPermission.class::isInstance)
                    .map(ApiPermission.class::cast)
                    .collect(Collectors.toList());
        }
        if (first instanceof Map<?, ?>) {
            return list.stream()
                    .map(item -> objectMapper.convertValue(item, ApiPermission.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void cacheToRedis(List<ApiPermission> rules) {
        try {
            redisClient.setEx(PERMISSION_CACHE_KEY, rules, REDIS_CACHE_TTL_SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.warn("缓存接口权限到Redis失败: {}", e.getMessage());
        }
    }

}
