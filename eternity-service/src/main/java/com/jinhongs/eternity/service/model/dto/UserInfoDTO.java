package com.jinhongs.eternity.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoDTO {

    /**
     * 全局唯一用户名
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 全局唯一用户名
     */
    @Schema(description = "全局唯一用户名")
    private String usercode;

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
