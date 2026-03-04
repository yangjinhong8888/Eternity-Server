package com.jinhongs.eternity.service.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {
    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "角色列表")
    private List<RoleVO> roles;

    @Schema(description = "创建时间")
    private Long createTime;
}
