package com.jinhongs.eternity.service.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文章详情视图对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {

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

    @Schema(description = "浏览量")
    private Integer viewCount;

    @Schema(description = "文章状态（10-草稿 20-已发布 30-已删除）")
    private Byte status;

    @Schema(description = "创建时间戳（毫秒）")
    private Long createTime;

    @Schema(description = "更新时间戳（毫秒）")
    private Long updateTime;

    @Schema(description = "标签列表")
    private List<TagVO> tags;
}
