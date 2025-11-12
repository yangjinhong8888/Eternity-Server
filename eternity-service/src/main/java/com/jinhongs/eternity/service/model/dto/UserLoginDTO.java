package com.jinhongs.eternity.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserLoginDTO {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
}
