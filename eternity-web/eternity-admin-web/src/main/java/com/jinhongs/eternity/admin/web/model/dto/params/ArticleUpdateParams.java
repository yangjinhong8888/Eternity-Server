package com.jinhongs.eternity.admin.web.model.dto.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ArticleUpdateParams {

    @NotNull(message = "文章ID不能为空")
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

    @Schema(description = "文章状态（10-草稿 20-发布）")
    private Byte status;
}
