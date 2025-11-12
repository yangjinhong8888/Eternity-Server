package com.jinhongs.eternity.common.enums.base;

/**
 * 枚举接口
 * code 一般使用 Integer String 类型
 *
 */
public interface Enumerable<T> {

    T getCode();

    String getTitle();

    boolean is(T code);
}
