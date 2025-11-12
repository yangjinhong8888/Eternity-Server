package com.jinhongs.eternity.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 响应实体类封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    /**
     * 响应码
     */
    private Integer code = 0;
    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
