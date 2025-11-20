package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.model.converter.ControllerUserConverter;
import com.jinhongs.eternity.admin.web.model.dto.params.UserLoginParams;
import com.jinhongs.eternity.admin.web.model.dto.params.UserRegisterParams;
import com.jinhongs.eternity.common.enums.ResponseCode;
import com.jinhongs.eternity.common.utils.result.ResponseResult;
import com.jinhongs.eternity.common.utils.result.ResponseResultUtils;
import com.jinhongs.eternity.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "登录接口")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "注册账号")
    @PostMapping("/register")
    @PermitAll // 允许所有来源访问
    public ResponseResult<String> register(@Valid @RequestBody UserRegisterParams userRegisterParams) {

        boolean register = userService.register(ControllerUserConverter.INSTANCE.toUserRegisterDTO(userRegisterParams));

        return register ? ResponseResultUtils.success() : ResponseResultUtils.result(ResponseCode.FAIL, "注册失败");
    }


    @Operation(summary = "登录接口")
    @PostMapping("/login")
    @PermitAll // 允许所有来源访问
    public ResponseResult<Void> login(@Valid @RequestBody UserLoginParams userLoginParams, HttpServletResponse response) {

        String uuid = userService.adminLogin(ControllerUserConverter.INSTANCE.toUserLoginDTO(userLoginParams));

        Cookie cookie = new Cookie("token", uuid);

        // 配置 Cookie 属性 cookie不设置过期时间，所有的过期在Redis上直接控制
        cookie.setPath("/");                  // 生效路径：全站
        cookie.setHttpOnly(true);             // 禁止 JavaScript 访问
        // cookie.setSecure(true);              // 确保cookie只在HTTPS环境下传输，开发环境下关闭
        response.addCookie(cookie);
        return ResponseResultUtils.success();
    }

    @Operation(summary = "查询用户信息接口")
    @PostMapping("/getUserInfo")
    public ResponseResult<String> getUserInfo() {
        return ResponseResultUtils.success("查到了用户信息");
    }
}
