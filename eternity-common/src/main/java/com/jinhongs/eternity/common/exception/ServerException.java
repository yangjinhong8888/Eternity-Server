package com.jinhongs.eternity.common.exception;

import com.jinhongs.eternity.common.enums.ResultCode;
import lombok.Getter;

/**
 * Eternity通用异常处理器
 */
@Getter
public class ServerException extends RuntimeException {
    private final Integer code;

    public ServerException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ServerException(String message) {
        super(message);
        this.code = ResultCode.FAIL.getCode();
    }

}