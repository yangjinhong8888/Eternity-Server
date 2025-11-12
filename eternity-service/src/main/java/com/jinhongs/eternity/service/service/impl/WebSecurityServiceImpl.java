package com.jinhongs.eternity.service.service.impl;

import com.jinhongs.eternity.dao.mysql.model.dto.UserEntity;
import com.jinhongs.eternity.dao.mysql.repository.UserInfoRepository;
import com.jinhongs.eternity.service.model.converter.ServiceUserConverter;
import com.jinhongs.eternity.service.model.dto.SecurityUserInfoDTO;
import com.jinhongs.eternity.service.service.WebSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebSecurityServiceImpl implements WebSecurityService {


    private final UserInfoRepository userInfoRepository;

    @Autowired
    public WebSecurityServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public SecurityUserInfoDTO getSecurityUserInfoByUsername(String userName) {
        UserEntity userEntity = userInfoRepository.findUserIdByUserName(userName);
        return ServiceUserConverter.INSTANCE.toSecurityUserInfoDTO(userEntity);
    }
}
