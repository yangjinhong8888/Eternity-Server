package com.jinhongs.eternity.view.web.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 自定义Security用户模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUserDetailsImpl implements UserDetails {

    private Long id;

    private String username;  // 用户名（Spring Security 默认要求字段名为 username）

    private String password; // 密码 （字段名必须为 password）

    // 非数据库字段，用于存储权限信息
    private List<GrantedAuthority> authorities;

    //----------------- 用户状态字段（目前账号没有这些功能，暂时全部设置为true） ------------------
    //    private boolean enabled = true;                // 是否启用
    //    private boolean accountNonExpired = true;     // 账户是否未过期
    //    private boolean accountNonLocked = true;      // 账户是否未锁定
    //    private boolean credentialsNonExpired = true; // 凭证是否未过期

    //----------------- UserDetails 接口方法实现 -----------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}