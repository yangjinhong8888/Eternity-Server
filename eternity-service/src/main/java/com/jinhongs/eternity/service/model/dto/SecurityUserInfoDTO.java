package com.jinhongs.eternity.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUserInfoDTO {

    /**
     * 全局唯一用户名
     */
    @Schema(description = "用户id")
    private long userId;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "昵称")
    private String password;

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<String> authorities;
}
