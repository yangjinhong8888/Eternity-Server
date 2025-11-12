package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.service.model.dto.UserLoginDTO;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;

public interface UserService {

    boolean register(UserRegisterDTO userRegisterDTO);

    String login(UserLoginDTO userLoginDTO);
}
