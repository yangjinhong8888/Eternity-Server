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
 * 评论表
 * </p>
 */
@Data
@TableName("comments")
@Schema(name = "Comments", description = "评论表")
public class Comments implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @Schema(description = "评论ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章ID
     */
    @TableField("article_id")
    @Schema(description = "文章ID")
    private Long articleId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 父评论ID
     */
    @TableField("parent_id")
    @Schema(description = "父评论ID")
    private Long parentId;

    /**
     * 评论内容
     */
    @TableField("content")
    @Schema(description = "评论内容")
    private String content;

    /**
     * 点赞数
     */
    @TableField("likes_count")
    @Schema(description = "点赞数")
    private Integer likesCount;

    /**
     * 评论状态 10-正常 20-删除
     */
    @TableField("status")
    @Schema(description = "评论状态 10-正常 20-删除")
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
