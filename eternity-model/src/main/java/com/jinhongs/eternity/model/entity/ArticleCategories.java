package com.jinhongs.eternity.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 文章分类表
 * </p>
 */
@Data
@TableName("article_categories")
@Schema(name = "ArticleCategories", description = "文章分类表")
public class ArticleCategories implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableField("article_id")
    @Schema(description = "文章ID")
    private Long articleId;

    /**
     * 分类ID
     */
    @TableField("category_id")
    @Schema(description = "分类ID")
    private Long categoryId;
}
