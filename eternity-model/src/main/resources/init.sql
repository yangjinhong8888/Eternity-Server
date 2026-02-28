use eternity;

-- 用户表
CREATE TABLE IF NOT EXISTS `user_info` (
    `id`           BIGINT      PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `username`     VARCHAR(64)     NOT NULL COMMENT '昵称',
    `avatar`       VARCHAR(255)    DEFAULT NULL COMMENT '头像URL',
    `create_time`  BIGINT      NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time`  BIGINT      NOT NULL COMMENT '更新时间戳（毫秒）'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表' ROW_FORMAT=DYNAMIC;

-- 用户认证表
CREATE TABLE IF NOT EXISTS `user_auth` (
    `id`            BIGINT      PRIMARY KEY AUTO_INCREMENT COMMENT '认证记录ID',
    `user_id`       BIGINT      NOT NULL COMMENT '用户ID',
    `identity_type` VARCHAR(20)     NOT NULL COMMENT '认证类型（username/phone/email/wechat）',
    `identifier`    VARCHAR(255)    NOT NULL COMMENT '唯一标识（用户名/手机号/邮箱/微信openID）',
    `credential`    VARCHAR(255)    DEFAULT NULL COMMENT '密码或令牌',
    `create_time`   BIGINT      NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time`   BIGINT      NOT NULL COMMENT '更新时间戳（毫秒）',
    UNIQUE INDEX idx_identity (identity_type, identifier)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户认证表' ROW_FORMAT=DYNAMIC;

-- 角色表
CREATE TABLE IF NOT EXISTS `role` (
    `id`           BIGINT      PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    `role_name`    VARCHAR(64)     NOT NULL COMMENT '角色名称',
    `role_key`     VARCHAR(64)     NOT NULL UNIQUE COMMENT '角色唯一标识键',
    `is_enable`    TINYINT     NOT NULL DEFAULT 1 COMMENT '是否启用 0-否 1-是',
    `create_time`  BIGINT      NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time`  BIGINT      NOT NULL COMMENT '更新时间戳（毫秒）'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表' ROW_FORMAT=DYNAMIC;

-- 权限表
CREATE TABLE IF NOT EXISTS `permission` (
    `id`           BIGINT      PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    `perm_name`    VARCHAR(64)     NOT NULL COMMENT '权限名称',
    `perm_key`     VARCHAR(255)    NOT NULL UNIQUE COMMENT '权限唯一标识符',
    `type`         VARCHAR(20) NOT NULL COMMENT '权限类型(user/article/comment等)',
    `parent_id`    BIGINT      NULL COMMENT '父权限ID',
    `create_time`  BIGINT      NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time`  BIGINT      NOT NULL COMMENT '更新时间戳（毫秒）',
    INDEX idx_parent (parent_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表' ROW_FORMAT=DYNAMIC;

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `user_role` (
    `id`           BIGINT      PRIMARY KEY AUTO_INCREMENT COMMENT '用户角色关联ID',
    `user_id`      BIGINT      NOT NULL COMMENT '用户ID',
    `role_id`      BIGINT      NOT NULL COMMENT '角色ID',
    `create_time`  BIGINT      NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time`  BIGINT      NOT NULL COMMENT '更新时间戳（毫秒）',
    UNIQUE KEY uk_user_role (user_id, role_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表' ROW_FORMAT=DYNAMIC;

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `role_permission` (
    `id`            BIGINT      PRIMARY KEY AUTO_INCREMENT COMMENT '角色权限关联ID',
    `role_id`       BIGINT    NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT    NOT NULL COMMENT '权限ID',
    `create_time`   BIGINT    NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time`   BIGINT    NOT NULL COMMENT '更新时间戳（毫秒）',
    UNIQUE KEY uk_role_permission (role_id, permission_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限关联表' ROW_FORMAT=DYNAMIC;

-- 文章表
CREATE TABLE IF NOT EXISTS `articles` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文章ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `title` VARCHAR(255) NOT NULL COMMENT '文章标题',
    `content_md` MEDIUMTEXT NOT NULL COMMENT '文章内容',
    `summary` TEXT NULL COMMENT '摘要',
    `cover_image` VARCHAR(255) NULL COMMENT '封面图地址',
    `view_count` INT DEFAULT 0 COMMENT '浏览量',
    `status` tinyint NOT NULL DEFAULT 10 COMMENT '文章状态 10-新建 20-发布 30-删除',
    `create_time` BIGINT NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT NOT NULL COMMENT '更新时间戳（毫秒）',
    INDEX idx_user_status (user_id, status)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表' ROW_FORMAT=DYNAMIC;

-- 标签表
CREATE TABLE IF NOT EXISTS `tags` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    `tag_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
    `create_time` BIGINT NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT NOT NULL COMMENT '更新时间戳（毫秒）'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签表' ROW_FORMAT=DYNAMIC;

-- 文章标签关联表
CREATE TABLE IF NOT EXISTS `article_tags` (
    `id`         BIGINT     PRIMARY KEY AUTO_INCREMENT COMMENT '文章标签关联ID',
    `article_id` BIGINT     NOT NULL COMMENT '文章ID',
    `tag_id`     BIGINT     NOT NULL COMMENT '标签ID',
    `create_time` BIGINT    NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT    NOT NULL COMMENT '更新时间戳（毫秒）',
    UNIQUE KEY uk_article_tag (article_id, tag_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签表' ROW_FORMAT=DYNAMIC;

-- 评论表
CREATE TABLE IF NOT EXISTS `comments` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    `article_id` BIGINT NOT NULL COMMENT '文章ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `parent_id` BIGINT NULL COMMENT '父评论ID',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `likes_count` INT DEFAULT 0 COMMENT '点赞数',
    `status` tinyint DEFAULT 10 COMMENT '评论状态 10-正常 20-删除',
    `create_time` BIGINT NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT    NOT NULL COMMENT '更新时间戳（毫秒）',
    INDEX idx_article (article_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表' ROW_FORMAT=DYNAMIC;

-- 文章点赞表
CREATE TABLE IF NOT EXISTS `article_likes` (
    `id`       BIGINT     PRIMARY KEY AUTO_INCREMENT COMMENT '文章点赞ID',
    `user_id`   BIGINT NOT NULL COMMENT '用户ID',
    `article_id` BIGINT NOT NULL COMMENT '文章ID',
    `create_time` BIGINT NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT    NOT NULL COMMENT '更新时间戳（毫秒）',
    UNIQUE KEY uk_user_article (user_id, article_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COLLATE=utf8mb4_0900_ai_ci COMMENT='文章点赞表' ROW_FORMAT=DYNAMIC;

-- 文章收藏表
CREATE TABLE IF NOT EXISTS `article_favorites` (
    `id`      BIGINT     PRIMARY KEY AUTO_INCREMENT COMMENT '文章收藏ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `article_id` BIGINT NOT NULL COMMENT '文章ID',
    `create_time` BIGINT NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT    NOT NULL COMMENT '更新时间戳（毫秒）',
    UNIQUE KEY uk_user_article (user_id, article_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章收藏表' ROW_FORMAT=DYNAMIC;

-- 分类表
CREATE TABLE IF NOT EXISTS `categories` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `parent_id` BIGINT NULL COMMENT '父分类ID',
    `sort_order` INT DEFAULT 0 COMMENT '排序权重',
    `create_time` BIGINT NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT    NOT NULL COMMENT '更新时间戳（毫秒）',
    INDEX idx_parent (parent_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表' ROW_FORMAT=DYNAMIC;

-- 文章分类表
CREATE TABLE IF NOT EXISTS `article_categories` (
    `id`      BIGINT     PRIMARY KEY AUTO_INCREMENT COMMENT '文章分类ID',
    `article_id` BIGINT NOT NULL COMMENT '文章ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `create_time` BIGINT NOT NULL COMMENT '创建时间戳（毫秒）',
    `update_time` BIGINT    NOT NULL COMMENT '更新时间戳（毫秒）',
    UNIQUE KEY uk_article_category (article_id, category_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章分类表' ROW_FORMAT=DYNAMIC;


INSERT into `user_info` (username, avatar, create_time, update_time) VALUES
    ('管理员', NULL, 1700000001000, 1700000001000);

INSERT INTO `user_auth` (user_id, identity_type, identifier, credential, create_time, update_time) VALUES
    (2, 'phone', '13800000000', "{noop}123456", 1700000001000, 1700000001000);
