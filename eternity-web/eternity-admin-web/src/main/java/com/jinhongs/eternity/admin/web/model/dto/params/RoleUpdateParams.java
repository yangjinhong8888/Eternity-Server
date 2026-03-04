package com.jinhongs.eternity.admin.web.model.dto.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleUpdateParams {
    @NotNull(message = "角色ID不能为空")
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @NotNull(message = "启用状态不能为空")
    private Byte isEnable;
}
