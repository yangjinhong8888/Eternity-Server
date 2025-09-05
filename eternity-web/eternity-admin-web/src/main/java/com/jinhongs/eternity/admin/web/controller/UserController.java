package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.model.converter.UserConverter;
import com.jinhongs.eternity.admin.web.model.dto.params.UserLoginParams;
import com.jinhongs.eternity.admin.web.model.dto.params.UserRegisterParams;
import com.jinhongs.eternity.service.model.dto.UserInfoDTO;
import com.jinhongs.eternity.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Tag(name="登录接口")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginParams userLoginParams, HttpServletResponse response) {

        String uuid = userService.login(UserConverter.INSTANCE.userLoginParamsToUserLoginDTO(userLoginParams));
        Cookie cookie = new Cookie("token", "token");

        // 配置 Cookie 属性
        cookie.setPath("/");                  // 生效路径：全站
        cookie.setHttpOnly(true);             // // 禁止 JavaScript 访问
        response.addCookie(cookie);
        return uuid;
    }

    @Operation(summary = "注册账号")
    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRegisterParams userRegisterParams) {

        UserInfoDTO userInfoDTO = userService.register(UserConverter.INSTANCE.userRegisterParamsToUserRegisterDTO(userRegisterParams));

        return "register";
    }

    @Operation(summary = "测试账号")
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
