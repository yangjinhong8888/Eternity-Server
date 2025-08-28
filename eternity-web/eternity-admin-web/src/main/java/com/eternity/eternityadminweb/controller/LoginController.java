package com.eternity.eternityadminweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name="登录接口")
public class LoginController {
    @Operation(summary = "普通login请求")
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
