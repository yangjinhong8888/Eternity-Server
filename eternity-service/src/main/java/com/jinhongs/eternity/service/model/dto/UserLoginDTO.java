package com.jinhongs.eternity.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserLoginDTO {
    /**
     * 全局唯一用户名
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String username;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL")
    private String avatar;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
}
