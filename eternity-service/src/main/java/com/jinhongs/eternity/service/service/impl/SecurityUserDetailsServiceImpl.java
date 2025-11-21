package com.jinhongs.eternity.service.service.impl;

import com.jinhongs.eternity.dao.mysql.model.dto.UserEntity;
import com.jinhongs.eternity.dao.mysql.repository.UserInfoRepository;
import com.jinhongs.eternity.service.model.converter.ServiceUserConverter;
import com.jinhongs.eternity.service.model.dto.security.SecurityUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 在SecurityConfig 中配置，这里不用配置@Service注入
 * 自定义Security用户模型信息查询，会被自动注入到UsernamePasswordAuthenticationToken 对应的 DaoAuthenticationProvider 中使用
 * Provider 是认证提供者，用于处理用户认证请求（比如账号密码验证或者其他登录方式的验证），并返回一个验证成功的 Authentication 对象。
 * Provider 和 AuthenticationToken 一一对应 共同配合完成一种认证方式
 * security 会根据不同的AuthenticationToken，自动调用对应的 Provider 来处理认证请求。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;


    @Override
    public SecurityUserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户基础信息
        UserEntity userEntity;
        try {
            userEntity = userInfoRepository.findUserIdByUserName(username);
        } catch (Exception e) {
            log.error("用户不存在: {}", username);
            throw new UsernameNotFoundException("用户不存在");
        }
        // 返回Security所需形式的用户信息
        return ServiceUserConverter.INSTANCE.toSecurityUserDetailsImpl(userEntity);
    }
}
