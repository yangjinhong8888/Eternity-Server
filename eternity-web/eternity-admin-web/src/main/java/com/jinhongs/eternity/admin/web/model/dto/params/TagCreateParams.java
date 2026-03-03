package com.jinhongs.eternity.admin.web.model.dto.params;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagCreateParams {
    @NotBlank(message = "标签名称不能为空")
    private String tagName;
}
