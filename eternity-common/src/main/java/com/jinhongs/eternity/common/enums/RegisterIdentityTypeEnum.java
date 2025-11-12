package com.jinhongs.eternity.common.enums;

import com.jinhongs.eternity.common.enums.base.Enumerable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegisterIdentityTypeEnum implements Enumerable<String> {
    USERNAME("username", "用户名"),
    PHONE("phone", "手机号"),
    EMAIL("email", "邮箱"),
    WECHAT("wechat", "微信");

    private final String code;

    private final String title;

    @Override
    public boolean is(String code) {
        return this.code.equals(code);
    }
}
