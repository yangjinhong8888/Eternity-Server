package com.jinhongs.eternity.admin.web.model.dto.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterParams {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度需为3-20字符")
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL")
    private String avatar;
}
