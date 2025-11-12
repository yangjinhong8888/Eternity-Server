package com.jinhongs.eternity.common.exception;

import com.jinhongs.eternity.common.enums.ResponseCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private final Integer code;

    public GeneralException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public GeneralException(String message) {
        super(message);
        this.code = ResponseCode.FAIL.getCode();
    }

}