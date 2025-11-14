package com.jinhongs.eternity.view.web.model.dto.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterParams {

    @NotBlank(message = "认证类型不能为空")
    @Schema(description = "认证类型（username/phone/email/wechat）")
    private String identityType;


    @NotBlank(message = "用户名/手机号/邮箱不能为空")
    @Size(min = 3, max = 20, message = "用户名/手机号/邮箱长度需为3-20字符")
    @Schema(description = "登录唯一标识")
    private String identifier;

    @NotBlank(message = "密码/验证码不能为空")
    @Schema(description = "凭证")
    private String credential;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL")
    private String avatar;
}
