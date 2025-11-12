package com.jinhongs.eternity.dao.mysql.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.jinhongs.eternity.dao.mysql.mapper.UserAuthMapper;
import com.jinhongs.eternity.dao.mysql.mapper.UserInfoMapper;
import com.jinhongs.eternity.model.entity.UserAuth;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户认证表 服务实现类
 * </p>
 */
@Component
public class UserAuthRepository extends CrudRepository<UserAuthMapper, UserAuth> {

}
