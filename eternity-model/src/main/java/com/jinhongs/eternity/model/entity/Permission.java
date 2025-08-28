package com.jinhongs.eternity.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 */
@Data
@TableName("permission")
@Schema(name = "Permission", description = "权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @Schema(description = "权限ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    @TableField("perm_name")
    @Schema(description = "权限名称")
    private String permName;

    /**
     * 权限唯一标识符
     */
    @TableField("perm_key")
    @Schema(description = "权限唯一标识符")
    private String permKey;

    /**
     * 权限类型(user/article/comment等)
     */
    @TableField("type")
    @Schema(description = "权限类型(user/article/comment等)")
    private String type;

    /**
     * 父权限ID
     */
    @TableField("parent_id")
    @Schema(description = "父权限ID")
    private Long parentId;

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
