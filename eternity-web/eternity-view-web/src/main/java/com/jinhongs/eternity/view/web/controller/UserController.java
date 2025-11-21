package com.jinhongs.eternity.view.web.controller;

import com.jinhongs.eternity.common.utils.result.Result;
import com.jinhongs.eternity.service.service.UserService;
import com.jinhongs.eternity.view.web.model.converter.ControllerUserConverter;
import com.jinhongs.eternity.view.web.model.dto.params.UserLoginParams;
import com.jinhongs.eternity.view.web.model.dto.params.UserRegisterParams;
import com.jinhongs.eternity.view.web.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "登录接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "注册账号")
    @PostMapping("/register")
    public ResponseEntity<Result<Void>> register(@Valid @RequestBody UserRegisterParams userRegisterParams) {

        boolean register = userService.register(ControllerUserConverter.INSTANCE.toUserRegisterDTO(userRegisterParams));

        return register ? ResultUtils.ok() : ResultUtils.badRequest("注册失败");
    }


    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public ResponseEntity<Result<Void>> login(@Valid @RequestBody UserLoginParams userLoginParams, HttpServletResponse response) {

        String uuid = userService.viewLogin(ControllerUserConverter.INSTANCE.toUserLoginDTO(userLoginParams));

        Cookie cookie = new Cookie("token", uuid);

        // 配置 Cookie 属性 配置 Cookie 属性 cookie不设置过期时间，所有的过期在Redis上直接控制
        cookie.setPath("/");                  // 生效路径：全站
        cookie.setHttpOnly(true);             // 禁止 JavaScript 访问
        // cookie.setSecure(true);              // 确保cookie只在HTTPS环境下传输，开发环境下关闭
        response.addCookie(cookie);
        return ResultUtils.ok();
    }

    @Operation(summary = "查询用户信息接口")
    @PostMapping("/getUserInfo")
    @PreAuthorize("isAuthenticated()") // 权限不足时，会抛出AuthorizationDeniedException 最后被RuntimeException 捕获
    public ResponseEntity<Result<Void>> getUserInfo() {
        return ResultUtils.ok();
    }
}
