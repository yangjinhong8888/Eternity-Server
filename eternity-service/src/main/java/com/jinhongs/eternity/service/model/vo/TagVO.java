package com.jinhongs.eternity.service.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签视图对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {

    @Schema(description = "标签ID")
    private Long id;

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "文章数量")
    private Integer articleCount;
}
