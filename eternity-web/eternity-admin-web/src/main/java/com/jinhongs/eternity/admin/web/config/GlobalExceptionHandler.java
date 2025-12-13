package com.jinhongs.eternity.admin.web.config;

import com.jinhongs.eternity.admin.web.utils.ResultUtils;
import com.jinhongs.eternity.common.exception.ClientException;
import com.jinhongs.eternity.common.exception.ServerException;
import com.jinhongs.eternity.common.utils.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

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
    public ResponseEntity<Result<Void>> handleValidationError(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
        log.error("MethodArgumentNotValidException参数校验失败：{}", errors);
        return ResultUtils.badRequest("参数校验失败");
    }

    /**
     * 处理请求参数校验失败（@RequestParam/@PathVariable + @Validated）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<Void>> handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();
        log.info("ConstraintViolationException参数校验失败：{}", errors);
        return ResultUtils.badRequest("参数校验失败");
    }

    /**
     * 处理表单参数绑定失败（@ModelAttribute）
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Result<Void>> handleBindException(BindException e) {
        List<String> errors = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        log.info("BindException参数校验失败：{}", errors);
        return ResultUtils.badRequest("参数校验失败");
    }


    /**
     * 处理请求参数缺失（如缺少必需的 @RequestParam）
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result<Void>> handleMissingParameter(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException: 参数名={}, 参数类型={}", e.getParameterName(), e.getParameterType());
        return ResultUtils.badRequest("请求参数缺失");
    }

    /**
     * 处理请求体解析失败（如 JSON 格式错误）
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<Void>> handleMessageNotReadable(HttpMessageNotReadableException e) {

        log.error("HttpMessageNotReadableException: 请求体解析失败", e);
        return ResultUtils.badRequest("请求体解析失败");
    }

    /**
     * 处理请求方法不支持（如用 GET 访问 POST 接口）
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException: 请求方法 {} 不被支持，支持的方法有 {}",
                e.getMethod(),
                String.join(", ", e.getSupportedMethods()));
        return ResultUtils.methodNotAllowed();
    }

    /**
     * 处理请求媒体类型不支持（如用 XML 请求 JSON 接口）
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Result<Void>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {

        log.error("HttpMediaTypeNotSupportedException: 不支持的媒体类型 {}", e.getContentType());
        return ResultUtils.unsupportedMediaType();
    }

    /**
     * 处理 404 未找到资源
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Result<Void>> handleNoHandlerFound(NoHandlerFoundException e) {

        log.error("NoHandlerFoundException: 请求路径={}，请求方法={}", e.getRequestURL(), e.getHttpMethod());
        return ResultUtils.notFound();
    }

    /**
     * 客户端通用异常处理器
     */
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Result<Void>> handleClientException(ClientException e) {
        log.error("ClientException: {}", e.getMessage());
        return ResultUtils.badRequest(e.getMessage());
    }

    /**
     * 服务端通用异常处理器
     */
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Result<Void>> handleServerException(ServerException e) {
        log.error("ServerException: {}", e.getMessage());
        return ResultUtils.internalServerError(e.getMessage());
    }

    /**
     * 未登录或身份凭证缺失
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Result<Void>> handleAuthenticationException(AuthenticationException e) {
        log.warn("未登录访问受保护资源: {}", e.getMessage());
        return ResultUtils.unauthorized("未登录");
    }

    /**
     * 权限不足
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("权限不足: {}", e.getMessage());
        return ResultUtils.forbidden("权限不足");
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<Void>> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage(), e);
        return ResultUtils.internalServerError();
    }

    /**
     * 处理其他未知异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleUnknownException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("Unknown exception occurred: {}", e.getMessage(), e);
        return ResultUtils.internalServerError(e.getMessage());
    }
}
