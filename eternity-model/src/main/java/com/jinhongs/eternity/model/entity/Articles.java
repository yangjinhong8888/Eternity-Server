package com.jinhongs.eternity.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 文章表
 * </p>
 */
@Data
@TableName("articles")
@Schema(name = "Articles", description = "文章表")
public class Articles implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @Schema(description = "文章ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 文章标题
     */
    @TableField("title")
    @Schema(description = "文章标题")
    private String title;

    /**
     * 文章内容
     */
    @TableField("content_md")
    @Schema(description = "文章内容")
    private String contentMd;

    /**
     * 摘要
     */
    @TableField("summary")
    @Schema(description = "摘要")
    private String summary;

    /**
     * 封面图地址
     */
    @TableField("cover_image")
    @Schema(description = "封面图地址")
    private String coverImage;

    /**
     * 浏览量
     */
    @TableField("view_count")
    @Schema(description = "浏览量")
    private Integer viewCount;

    /**
     * 文章状态 10-新建 20-发布 30-删除
     */
    @TableField("status")
    @Schema(description = "文章状态 10-新建 20-发布 30-删除")
    private Byte status;

    /**
     * 创建时间戳（毫秒）
     */
    @Schema(description = "创建时间戳（毫秒）")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 更新时间戳（毫秒）
     */
    @Schema(description = "更新时间戳（毫秒）")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;
}
