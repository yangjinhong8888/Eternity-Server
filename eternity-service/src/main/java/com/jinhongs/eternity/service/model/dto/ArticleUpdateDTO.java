package com.jinhongs.eternity.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 更新文章请求数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateDTO {

    @Schema(description = "文章ID")
    private Long id;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章内容（HTML格式）")
    private String content;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "封面图地址")
    private String coverImage;

    @Schema(description = "标签ID列表")
    private List<Long> tagIds;

    /**
     * 文章状态：10-草稿，20-发布
     */
    @Schema(description = "文章状态（10-草稿 20-发布）")
    private Byte status;
}
