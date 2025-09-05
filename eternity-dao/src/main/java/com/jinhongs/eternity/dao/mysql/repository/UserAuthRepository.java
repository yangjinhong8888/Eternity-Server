package com.jinhongs.eternity.dao.mysql.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinhongs.eternity.dao.mysql.mapper.UserInfoMapper;
import com.jinhongs.eternity.model.entity.UserAuth;
import com.jinhongs.eternity.dao.mysql.mapper.UserAuthMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.jinhongs.eternity.model.entity.UserInfo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户认证表 服务实现类
 * </p>
 */
@Component
public class UserAuthRepository extends CrudRepository<UserAuthMapper, UserAuth> {

    private final UserInfoMapper userInfoMapper;

    public UserAuthRepository(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    public UserInfo findUserIdByUserName(String username){
        UserAuth userAuth = this.baseMapper.selectOne(
            new LambdaQueryWrapper<UserAuth>().eq(UserAuth::getIdentifier, username)
        );
        UserInfo userInfo = null;
        if (userAuth != null) {
            userInfo = userInfoMapper.selectOne(
                new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userAuth.getUserId())
            );
        }
        return userInfo;
    }

}
