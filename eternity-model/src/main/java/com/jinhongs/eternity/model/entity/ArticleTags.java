package com.jinhongs.eternity.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 文章标签表
 * </p>
 */
@Data
@TableName("article_tags")
@Schema(name = "ArticleTags", description = "文章标签表")
public class ArticleTags implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableField("article_id")
    @Schema(description = "文章ID")
    private Long articleId;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    @Schema(description = "标签ID")
    private Long tagId;
}
