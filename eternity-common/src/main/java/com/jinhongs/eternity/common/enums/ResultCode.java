package com.jinhongs.eternity.common.enums;

import com.jinhongs.eternity.common.enums.base.Enumerable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode implements Enumerable<Object> {
    // 常用HTTP状态码
    BAD_REQUEST(400, "错误请求"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "未找到资源"),
    METHOD_NOT_ALLOWED(405, "方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    INTERNAL_SERVER_ERROR(500, "服务器错误"),
    // 下面是业务状态码
    SUCCESS(0, "成功"),
    FAIL(10000, "失败"),
    VALIDATE_FAILED(1000, "验证失败"),
    SYSTEM_ERROR(1001, "系统错误"),
    DATA_EXISTS(1002, "数据已存在"),
    OPERATION_TOO_FREQUENT(1003, "操作过于频繁"),
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
