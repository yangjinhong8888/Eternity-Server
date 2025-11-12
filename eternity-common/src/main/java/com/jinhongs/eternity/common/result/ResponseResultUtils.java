package com.jinhongs.eternity.common.result;

import com.jinhongs.eternity.common.enums.ResponseCode;

public class ResponseResultUtils {

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> result(Integer code, String message) {
        return new ResponseResult<>(code, message);
    }

    public static <T> ResponseResult<T> result(ResponseCode responseEnum) {
        return result(responseEnum.getCode(), responseEnum.getMessage());
    }

    public static <T> ResponseResult<T> result(ResponseCode responseEnum, String message) {
        return result(responseEnum.getCode(), message);
    }
}
