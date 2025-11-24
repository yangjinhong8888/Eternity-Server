package com.jinhongs.eternity.admin.web.security;

import com.jinhongs.eternity.common.enums.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 认证失败处理类
 */
@Slf4j
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(ResultCode.UNAUTHORIZED.getCode());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(
                String.format("{\"code\": %d, \"message\": \"%s\"}", 401, "未登录")
        );
    }
}
