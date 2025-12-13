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

    // admin用户登录的token存储
    public static final String BASE_ADMIN_SESSION_TOKEN = BASE + "admin:session:token:%s";
    // admin每个用户id的登录平台&数量存储
    public static final String BASE_ADMIN_SESSION_PLATFORM_USER = BASE + "admin:session:user:%s:%d";

    // view用户登录的token存储
    public static final String BASE_VIEW_SESSION_TOKEN = BASE + "view:session:token:%s";
    // view每个用户id的登录平台&数量存储
    public static final String BASE_VIEW_SESSION_PLATFORM_USER = BASE + "view:session:user:%s:%d";

    // admin接口对应权限
    public static final String BASE_ADMIN_METHOD_PERMISSION_CACHE = BASE_CACHE + "admin:method:permission";
    // view接口对应权限
    public static final String BASE_VIEW_METHOD_PERMISSION_CACHE = BASE_CACHE + "view:method:permission";

    public static String getAdminSessionToken(String token) {
        return String.format(BASE_ADMIN_SESSION_TOKEN, token);
    }

    public static String getAdminSessionUser(String platform, long userId) {
        return String.format(BASE_ADMIN_SESSION_PLATFORM_USER, platform, userId);
    }

    public static String getViewSessionToken(String token) {
        return String.format(BASE_VIEW_SESSION_TOKEN, token);
    }

    public static String getViewSessionUser(String platform, long userId) {
        return String.format(BASE_VIEW_SESSION_PLATFORM_USER, platform, userId);
    }
}
