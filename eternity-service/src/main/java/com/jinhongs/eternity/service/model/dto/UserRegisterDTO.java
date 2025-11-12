package com.jinhongs.eternity.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRegisterDTO {
    /**
     * 全局唯一用户名
     */
    @Schema(description = "用户id")
    private long userId;

    /**
     * 认证类型（username/phone/email/wechat）
     */
    @Schema(description = "认证类型（username/phone/email/wechat）")
    private String identityType;

    /**
     * 登录唯一标识
     */
    @Schema(description = "登录唯一标识")
    private String identifier;

    /**
     * 密码
     */
    @Schema(description = "凭证")
    private String credential;

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
}
