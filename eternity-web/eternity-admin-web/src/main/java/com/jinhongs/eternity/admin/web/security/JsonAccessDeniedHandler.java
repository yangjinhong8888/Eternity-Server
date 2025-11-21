package com.jinhongs.eternity.admin.web.security;

import com.jinhongs.eternity.common.enums.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 自定义权限不足处理器
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(
                String.format("{\"code\": %d, \"msg\": \"%s\"}", 403, "权限不足，禁止访问，请联系管理员")
        );
    }
}
