package com.jinhongs.eternity.service.model.dto.security;

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
    //  接口中定义了 isEnabled() （或类似）方法，Jackson默认把它当成属性处理,所以这里定义出来，否则反序列化的时候会报错字段未找到
    private boolean enabled = true;                // 是否启用
    private boolean accountNonExpired = true;     // 账户是否未过期
    private boolean accountNonLocked = true;      // 账户是否未锁定
    private boolean credentialsNonExpired = true; // 凭证是否未过期

    //----------------- UserDetails 接口方法实现 -----------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities == null ? List.of() : this.authorities;
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
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
