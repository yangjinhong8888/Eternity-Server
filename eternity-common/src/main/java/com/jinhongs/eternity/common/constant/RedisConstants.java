package com.jinhongs.eternity.common.constant;

/**
 * Redis相关的缓存信息常量
 *
 */
public class RedisConstants {
    public static final String BASE = "eternity:";

    /**
     * 缓存或不重要的数据，可随意删除或自行恢复
     */
    public static final String BASE_CACHE = BASE + "cache:";

    /**
     * 数据
     */
    public static final String BASE_DATA = BASE + "data:";

    /**
     * 登录凭证
     */
    public static final String BASE_SESSION = BASE + "session:";

    public static final String BASE_SESSION_ADMIN = BASE_SESSION + "admin:";
    public static final String BASE_SESSION_VIEW = BASE_SESSION + "view:";

}
