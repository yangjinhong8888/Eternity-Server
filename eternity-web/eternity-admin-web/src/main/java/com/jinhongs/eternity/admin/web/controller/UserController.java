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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseResult<String> register(@Valid @RequestBody UserRegisterParams userRegisterParams) {

        boolean register = userService.register(ControllerUserConverter.INSTANCE.toUserRegisterDTO(userRegisterParams));

        return register ? ResponseResultUtils.success() : ResponseResultUtils.result(ResponseCode.FAIL, "注册失败");
    }


    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public ResponseResult<Void> login(@Valid @RequestBody UserLoginParams userLoginParams, HttpServletResponse response) {

        String uuid = userService.login(ControllerUserConverter.INSTANCE.toUserLoginDTO(userLoginParams));

        Cookie cookie = new Cookie("token", uuid);

        // 配置 Cookie 属性
        cookie.setPath("/");                  // 生效路径：全站
        cookie.setHttpOnly(true);             // 禁止 JavaScript 访问
        cookie.setMaxAge(60 * 60 * 24 * 15);  // 登录15天过期
        response.addCookie(cookie);
        return ResponseResultUtils.success();
    }

    @Operation(summary = "查询用户信息接口")
    @PostMapping("/getUserInfo")
    public ResponseResult<String> getUserInfo() {
        return ResponseResultUtils.success("查到了用户信息");
    }
}
