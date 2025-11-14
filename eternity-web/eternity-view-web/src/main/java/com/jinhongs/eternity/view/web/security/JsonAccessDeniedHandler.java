package com.jinhongs.eternity.view.web.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 自定义权限不足处理器
 */
@Slf4j
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(
                String.format("{\"code\": %d, \"msg\": \"%s %s\"}", 403, "权限不足，请联系管理员", accessDeniedException.getMessage())
        );
    }
}
