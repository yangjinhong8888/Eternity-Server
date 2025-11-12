package com.jinhongs.eternity.common.annotation;

import com.jinhongs.eternity.common.enums.base.Enumerable;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 枚举校验注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface EnumerableValid {

    Class<? extends Enumerable<?>> value();

    String message() default "枚举项不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}