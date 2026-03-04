package com.jinhongs.eternity.admin.web.model.dto.params;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleCreateParams {
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @NotBlank(message = "角色标识不能为空")
    private String roleKey;
}
