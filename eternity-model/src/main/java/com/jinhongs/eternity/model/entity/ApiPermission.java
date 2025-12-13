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
 * 
 * </p>
 */
@Data
@TableName("api_permission")
@Schema(name = "ApiPermission", description = "接口权限关联表")
public class ApiPermission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口路径（支持Ant风格，例如 /user/**）
     */
    @TableField("path")
    @Schema(description = "接口路径（支持Ant风格，例如 /user/**）")
    private String path;

    /**
     * HTTP方法，NULL表示所有方法
     */
    @TableField("http_method")
    @Schema(description = "HTTP方法，NULL表示所有方法")
    private String httpMethod;

    /**
     * 所需权限标识，对应permission里的权限
     */
    @TableField("perm_key")
    @Schema(description = "所需权限标识，对应permission里的权限")
    private String permKey;

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
