package com.jinhongs.eternity.admin.web.model.dto.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagUpdateParams {
    @NotNull(message = "标签ID不能为空")
    private Long id;

    @NotBlank(message = "标签名称不能为空")
    private String tagName;
}
