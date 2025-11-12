package com.jinhongs.eternity.common.enums;

import com.jinhongs.eternity.common.enums.base.Enumerable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode implements Enumerable<Object> {
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    UNSUPPORTED_MEDIA_TYPE(415, "请求媒体类型不支持"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    PARAM_INVALID(1000, "参数无效"),
    PARAM_MISS(1001, "请求参数缺失"),
    SQL_ERROR(2000, "数据库异常"),
    NETWORK_ERROR(2001, "网络异常请重试"),
    UNKNOWN_ERROR(9999, "未知错误");

    private final Integer code;
    private final String message;


    @Override
    public String getTitle() {
        return this.message;
    }


    @Override
    public boolean is(Object code) {
        return this.code.equals(code);
    }
}
