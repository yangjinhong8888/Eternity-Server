package com.jinhongs.eternity.service.service.impl;

import com.jinhongs.eternity.dao.mysql.repository.*;
import com.jinhongs.eternity.dao.redis.client.RedisClient;
import com.jinhongs.eternity.model.entity.*;
import com.jinhongs.eternity.service.model.dto.SecurityUserInfoDTO;
import com.jinhongs.eternity.service.service.WebSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WebSecurityServiceImpl implements WebSecurityService {

    private final RedisClient redisClient;

    private final UserInfoRepository userInfoRepository;

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public WebSecurityServiceImpl(RedisClient redisClient, UserInfoRepository userInfoRepository, UserAuthRepository userAuthRepository) {
        this.redisClient = redisClient;
        this.userInfoRepository = userInfoRepository;
        this.userAuthRepository = userAuthRepository;
    }
    @Override
    public SecurityUserInfoDTO getSecurityUserInfoByUsername(String userName) {
        UserInfo userInfo = userAuthRepository.findUserIdByUserName(userName);

        if (userInfo == null){
            return null;
        }

        // 获取用户权限列表
        List<String> permKeys = userInfoRepository.findUserPermKeyBuId(userInfo.getId());

        return new SecurityUserInfoDTO(
            userInfo.getId(),
            userInfo.getUsername(),
            userInfo.getAvatar(),
            permKeys
        );
    }
}
