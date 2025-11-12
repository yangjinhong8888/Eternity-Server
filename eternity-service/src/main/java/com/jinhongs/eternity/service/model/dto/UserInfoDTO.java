package com.jinhongs.eternity.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoDTO {

    /**
     * 全局唯一用户名
     */
    @Schema(description = "用户id")
    private long userId;

    @Schema(description = "认证类型（username/phone/email/wechat）")
    private String identityType;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String identifier;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL")
    private String avatar;
}
