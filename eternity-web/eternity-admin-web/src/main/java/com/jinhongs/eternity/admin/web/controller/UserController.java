package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.model.converter.ControllerUserConverter;
import com.jinhongs.eternity.admin.web.model.dto.params.UserLoginParams;
import com.jinhongs.eternity.admin.web.model.dto.params.UserRegisterParams;
import com.jinhongs.eternity.admin.web.model.dto.vo.UserInfoVO;
import com.jinhongs.eternity.admin.web.utils.ResultUtils;
import com.jinhongs.eternity.admin.web.security.annotation.PassAll;
import com.jinhongs.eternity.common.enums.ResultCode;
import com.jinhongs.eternity.common.utils.result.Result;
import com.jinhongs.eternity.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "登录接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "注册账号")
    @PassAll
    @PostMapping("/register")
    public ResponseEntity<Result<Void>> register(@Valid @RequestBody UserRegisterParams userRegisterParams) {

        boolean register = userService.register(ControllerUserConverter.INSTANCE.toUserRegisterDTO(userRegisterParams));

        return register ? ResultUtils.ok() : ResultUtils.badRequest("注册失败");
    }


    @Operation(summary = "登录接口")
    @PassAll
    @PostMapping("/login")
    public ResponseEntity<Result<Void>> login(@Valid @RequestBody UserLoginParams userLoginParams, HttpServletResponse response) {

        String uuid = userService.adminLogin(ControllerUserConverter.INSTANCE.toUserLoginDTO(userLoginParams));

        Cookie cookie = new Cookie("token", uuid);

        // 配置 Cookie 属性 cookie不设置过期时间，所有的过期在Redis上直接控制
        cookie.setPath("/");                  // 生效路径：全站
        cookie.setHttpOnly(true);             // 禁止 JavaScript 访问
        // cookie.setSecure(true);              // 确保cookie只在HTTPS环境下传输，开发环境下关闭
        response.addCookie(cookie);
        String finalMessage = StringUtils.hasText("message") ? "message" : ResultCode.SUCCESS.getMessage();
        log.info("成功响应结果： {}", finalMessage);
        return ResultUtils.ok();
    }

    @Operation(summary = "查询用户信息接口")
    @PostMapping("/getUserInfo")
    public ResponseEntity<Result<UserInfoVO>> getUserInfo() {
        return ResultUtils.ok(new UserInfoVO("李华"));
    }
}
