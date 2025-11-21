package com.jinhongs.eternity.view.web.utils;

import com.jinhongs.eternity.common.enums.ResultCode;
import com.jinhongs.eternity.common.utils.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

/**
 * 统一响应工具类（依据ResponseEntity构建）
 */
public class ResultUtils {

    private ResultUtils() {
        throw new IllegalStateException("Utility class");
    }

    // ==================== 直接返回 ResponseEntity 的成功方法 ====================

    /**
     * 成功响应（200 + 无数据）
     */
    public static <T> ResponseEntity<Result<T>> ok() {
        return ResponseEntity.ok(buildSuccessResult(null));
    }

    /**
     * 成功响应（200 + 数据）
     */
    public static <T> ResponseEntity<Result<T>> ok(T data) {
        return ResponseEntity.ok(buildSuccessResult(data));
    }

    /**
     * 成功响应（200 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> ok(String message) {
        return ResponseEntity.ok(buildSuccessResult(null, message));
    }

    /**
     * 成功响应（200 + 自定义消息和数据）
     */
    public static <T> ResponseEntity<Result<T>> ok(String message, T data) {
        return ResponseEntity.ok(buildSuccessResult(data, message));
    }

    /**
     * 创建成功（201 + 无数据）
     */
    public static <T> ResponseEntity<Result<T>> created() {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildSuccessResult(null));
    }

    /**
     * 创建成功（201 + 数据）
     */
    public static <T> ResponseEntity<Result<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildSuccessResult(data));
    }

    /**
     * 创建成功（201 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> created(String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildSuccessResult(null, message));
    }

    /**
     * 创建成功（201 + 自定义消息和数据）
     */
    public static <T> ResponseEntity<Result<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildSuccessResult(data, message));
    }

    /**
     * 接受请求（202 - 异步处理）
     */
    public static <T> ResponseEntity<Result<T>> accepted() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildSuccessResult(null, "请求已接受"));
    }

    /**
     * 接受请求（202 - 异步处理 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> accepted(String message) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildSuccessResult(null, message));
    }

    // ==================== 直接返回 ResponseEntity 的失败方法 ====================

    /**
     * 错误请求（400）
     */
    public static <T> ResponseEntity<Result<T>> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResult(ResultCode.VALIDATE_FAILED));
    }

    /**
     * 错误请求（400 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResult(ResultCode.VALIDATE_FAILED.getCode(), message));
    }

    /**
     * 错误请求（400 + 业务状态码和消息）
     */
    public static <T> ResponseEntity<Result<T>> badRequest(Integer code, String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResult(code, message));
    }

    /**
     * 错误请求（400 + ResultCode）
     */
    public static <T> ResponseEntity<Result<T>> badRequest(ResultCode resultCode) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResult(resultCode));
    }

    /**
     * 未授权（401）
     */
    public static <T> ResponseEntity<Result<T>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResult(ResultCode.UNAUTHORIZED));
    }

    /**
     * 未授权（401 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResult(ResultCode.UNAUTHORIZED.getCode(), message));
    }

    /**
     * 禁止访问（403）
     */
    public static <T> ResponseEntity<Result<T>> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildErrorResult(ResultCode.FORBIDDEN));
    }

    /**
     * 禁止访问（403 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildErrorResult(ResultCode.FORBIDDEN.getCode(), message));
    }

    /**
     * 资源不存在（404）
     */
    public static <T> ResponseEntity<Result<T>> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResult(ResultCode.NOT_FOUND));
    }

    /**
     * 资源不存在（404 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResult(ResultCode.NOT_FOUND.getCode(), message));
    }

    /**
     * 方法不允许（405）
     */
    public static <T> ResponseEntity<Result<T>> methodNotAllowed() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(buildErrorResult(ResultCode.METHOD_NOT_ALLOWED));
    }

    /**
     * 不支持的媒体类型（415）
     */
    public static <T> ResponseEntity<Result<T>> unsupportedMediaType() {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(buildErrorResult(ResultCode.UNSUPPORTED_MEDIA_TYPE));
    }

    /**
     * 请求超时（408）
     */
    public static <T> ResponseEntity<Result<T>> timeout() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(buildErrorResult(ResultCode.REQUEST_TIMEOUT));
    }

    /**
     * 服务器错误（500）
     */
    public static <T> ResponseEntity<Result<T>> internalServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResult(ResultCode.SYSTEM_ERROR));
    }

    /**
     * 服务器错误（500 + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResult(ResultCode.SYSTEM_ERROR.getCode(), message));
    }

    /**
     * 服务不可用（503）
     */
    public static <T> ResponseEntity<Result<T>> serviceUnavailable() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(buildErrorResult(503, "服务不可用"));
    }
    // ==================== 完全自定义响应 ====================

    /**
     * 自定义响应（状态码 + 业务码 + 消息 + 数据）
     */
    public static <T> ResponseEntity<Result<T>> custom(HttpStatus httpStatus, Integer code, String message, T data) {
        Result<T> result = new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setData(data);
        return ResponseEntity.status(httpStatus).body(result);
    }

    /**
     * 自定义响应（状态码 + ResultCode）
     */
    public static <T> ResponseEntity<Result<T>> custom(HttpStatus httpStatus, ResultCode resultCode) {
        return ResponseEntity.status(httpStatus).body(buildErrorResult(resultCode));
    }

    /**
     * 自定义响应（状态码 + ResultCode + 自定义消息）
     */
    public static <T> ResponseEntity<Result<T>> custom(HttpStatus httpStatus, ResultCode resultCode, String message) {
        return ResponseEntity.status(httpStatus).body(buildErrorResult(resultCode.getCode(), message));
    }

    /**
     * 自定义响应（状态码 + 业务码 + 消息）
     */
    public static <T> ResponseEntity<Result<T>> custom(HttpStatus httpStatus, Integer code, String message) {
        return ResponseEntity.status(httpStatus).body(buildErrorResult(code, message));
    }

    // ==================== 私有构建方法 ====================

    /**
     * 构建成功结果
     */
    private static <T> Result<T> buildSuccessResult(T data) {
        return buildSuccessResult(data, ResultCode.SUCCESS.getMessage());
    }

    /**
     * 构建成功结果（自定义消息）
     */
    private static <T> Result<T> buildSuccessResult(T data, String message) {
        String finalMessage = StringUtils.hasText(message) ? message : ResultCode.SUCCESS.getMessage();
        return new Result<T>()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMessage(finalMessage)
                .setData(data);
    }

    /**
     * 构建错误结果
     */
    private static <T> Result<T> buildErrorResult(ResultCode resultCode) {
        return new Result<T>()
                .setCode(resultCode.getCode())
                .setMessage(resultCode.getMessage());
    }

    /**
     * 构建错误结果（自定义消息）
     */
    private static <T> Result<T> buildErrorResult(Integer code, String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message);
    }

    // ==================== 条件判断方法 ====================

    /**
     * 如果条件为真返回成功，否则返回失败
     */
    public static <T> ResponseEntity<Result<T>> condition(boolean condition, T successData, String errorMessage) {
        return condition ? ok(successData) : badRequest(errorMessage);
    }

    /**
     * 如果条件为真返回成功，否则返回指定的错误响应
     */
    public static <T> ResponseEntity<Result<T>> condition(boolean condition, T successData,
                                                          ResponseEntity<Result<T>> errorResponse) {
        return condition ? ok(successData) : errorResponse;
    }

    /**
     * 如果对象不为空返回成功，否则返回数据不存在
     */
    public static <T> ResponseEntity<Result<T>> notNull(T obj) {
        return obj != null ? ok(obj) : notFound();
    }

    /**
     * 如果对象不为空返回成功，否则返回自定义消息的数据不存在
     */
    public static <T> ResponseEntity<Result<T>> notNull(T obj, String errorMessage) {
        return obj != null ? ok(obj) : notFound(errorMessage);
    }
}
