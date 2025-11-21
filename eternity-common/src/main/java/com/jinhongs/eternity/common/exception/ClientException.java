package com.jinhongs.eternity.common.exception;

import com.jinhongs.eternity.common.enums.ResultCode;
import lombok.Getter;

/**
 * Eternity通用异常处理器
 */
@Getter
public class ClientException extends RuntimeException {
    private final Integer code;

    public ClientException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ClientException(String message) {
        super(message);
        this.code = ResultCode.FAIL.getCode();
    }

}