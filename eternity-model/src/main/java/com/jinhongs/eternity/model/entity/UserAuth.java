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
 * 用户认证表
 * </p>
 */
@Data
@TableName("user_auth")
@Schema(name = "UserAuth", description = "用户认证表")
public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 认证记录ID
     */
    @Schema(description = "认证记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 认证类型（phone/email/wechat）
     */
    @TableField("identity_type")
    @Schema(description = "认证类型（phone/email/wechat）")
    private String identityType;

    /**
     * 唯一标识（手机号/OpenID等）
     */
    @TableField("identifier")
    @Schema(description = "唯一标识（手机号/OpenID等）")
    private String identifier;

    /**
     * 密码或令牌
     */
    @TableField("credential")
    @Schema(description = "密码或令牌")
    private byte[] credential;

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
