package com.jinhongs.eternity.common.utils.result;

import com.jinhongs.eternity.common.enums.ResultCode;
import lombok.Getter;

/**
 * <p>
 * 响应实体类封装
 */
@Getter
public class Result<T> {
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    // 链式调用的setter方法
    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Result<T> setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 判断业务代码是否成功(这个方法会在响应体里生成一个 success: xx，是对象序列化库的自动操作)
     */
    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.code);
    }
}
