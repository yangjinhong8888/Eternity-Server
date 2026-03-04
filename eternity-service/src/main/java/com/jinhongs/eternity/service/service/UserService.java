package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.common.utils.result.PageResult;
import com.jinhongs.eternity.service.model.dto.UserLoginDTO;
import com.jinhongs.eternity.service.model.dto.UserRegisterDTO;
import com.jinhongs.eternity.service.model.vo.UserVO;

import java.util.List;

public interface UserService {

    boolean register(UserRegisterDTO userRegisterDTO);

    String adminLogin(UserLoginDTO userLoginDTO);

    String viewLogin(UserLoginDTO userLoginDTO);

    PageResult<UserVO> listUsers(int page, int size);

    void assignRoles(Long userId, List<Long> roleIds);
}
