package com.jinhongs.eternity.service.service.impl;

import com.jinhongs.eternity.dao.mysql.repository.*;
import com.jinhongs.eternity.dao.redis.client.RedisClient;
import com.jinhongs.eternity.model.entity.*;
import com.jinhongs.eternity.service.model.converter.ServiceUserConverter;
import com.jinhongs.eternity.service.model.dto.UserInfoDTO;
import com.jinhongs.eternity.service.model.dto.UserLoginDTO;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;
import com.jinhongs.eternity.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final RedisClient redisClient;

    private final UserInfoRepository userInfoRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(RedisClient redisClient, UserInfoRepository userInfoRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.redisClient = redisClient;
        this.userInfoRepository = userInfoRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserInfoDTO register(UserRegisterDTO userRegisterDTO) {
        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
//        UserInfo userInfo = ServiceUserConverter.INSTANCE.userRegisterDTOToUserInfo(userRegisterDTO);
//        UserAuth userAuth = ServiceUserConverter.INSTANCE.userRegisterDTOToUserAuth(userRegisterDTO);
        return null;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        // 1. 创建未认证的 Token
        Authentication authRequest = new UsernamePasswordAuthenticationToken(
            userLoginDTO.getUsername(),
            userLoginDTO.getPassword()
        );

        // 2. 触发完整认证流程
        // 这里会调用默认的UsernamePasswordAuthenticationToken处理Provider DaoAuthenticationProvider进行用户名密码的验证处理
        // 验证失败则抛出异常(security已经封装好了UsernamePasswordAuthentication常用的异常处理，这里直接抛出就行，不用try)，验证成功时返回一个验证成功的对象
        Authentication authentication = authenticationManager.authenticate(authRequest);

        // 3. 手动设置上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 生成令牌或返回用户信息
        // 生成一个uuid，并吧对应的用户信息存储在redis中
        return "UUID";
    }

    @Override
    public UserInfoDTO getUserInfo(String username) {

        UserInfo userInfo = userInfoRepository.getById(username);

        return ServiceUserConverter.INSTANCE.userInfoToUserInfoDTO(userInfo);
    }
}
