package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.service.model.dto.UserInfoDTO;
import com.jinhongs.eternity.service.model.dto.UserLoginDTO;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;

public interface UserService {

    UserInfoDTO register(UserRegisterDTO userRegisterDTO);

    String login(UserLoginDTO userLoginDTO);

    UserInfoDTO getUserInfo(String userId);
}
