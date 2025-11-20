package com.jinhongs.eternity.view.web.config;

import com.jinhongs.eternity.common.enums.ResponseCode;
import com.jinhongs.eternity.common.exception.GeneralException;
import com.jinhongs.eternity.common.utils.result.ResponseResult;
import com.jinhongs.eternity.common.utils.result.ResponseResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理请求体参数校验失败（@RequestBody + @Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> handleValidationError(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
        log.error("MethodArgumentNotValidException参数校验失败：{}", errors);
        return ResponseResultUtils.result(ResponseCode.PARAM_INVALID);
    }

    /**
     * 处理请求参数校验失败（@RequestParam/@PathVariable + @Validated）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<Void> handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();
        log.info("ConstraintViolationException参数校验失败：{}", errors);
        return ResponseResultUtils.result(ResponseCode.PARAM_INVALID);
    }

    /**
     * 处理表单参数绑定失败（@ModelAttribute）
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<Void> handleBindException(BindException e) {
        List<String> errors = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        log.info("BindException参数校验失败：{}", errors);
        return ResponseResultUtils.result(ResponseCode.PARAM_INVALID);
    }

    /**
     * 处理请求方法不支持（如用 GET 访问 POST 接口）
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return ResponseResultUtils.result(ResponseCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 处理请求媒体类型不支持（如用 XML 请求 JSON 接口）
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult<Void> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        return ResponseResultUtils.result(ResponseCode.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 处理请求参数缺失（如缺少必需的 @RequestParam）
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult<Void> handleMissingParameter(MissingServletRequestParameterException e) {
        return ResponseResultUtils.result(ResponseCode.PARAM_MISS);
    }

    /**
     * 处理请求体解析失败（如 JSON 格式错误）
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult<Void> handleMessageNotReadable(HttpMessageNotReadableException e) {
        return ResponseResultUtils.result(ResponseCode.PARAM_INVALID);
    }

    /**
     * 处理 404 未找到资源
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseResult<Void> handleNoHandlerFound(NoHandlerFoundException e) {
        return ResponseResultUtils.result(ResponseCode.NOT_FOUND);
    }

    /**
     * Eternity通用异常处理器
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseResult<Void> handleNoHandlerFound(GeneralException e) {
        return ResponseResultUtils.result(ResponseCode.FAIL, e.getMessage());
    }

    /**
     * 处理自定义业务异常
     */
    // @ExceptionHandler(CustomException.class)
    // public ResponseResult<Void> handleBusinessException(CustomException e) {
    //     return ResponseResultUtils.result(ResponseCode.PARAM_INVALID);
    // }


    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<Void> handleNoHandlerFound(RuntimeException e) {
        log.info("异常");
        return ResponseResultUtils.result(ResponseCode.FAIL, e.getMessage());
    }

    /**
     * 处理其他未知异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleUnknownException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("出错了：{}", e);
        return ResponseResultUtils.result(ResponseCode.UNKNOWN_ERROR);
    }
}
