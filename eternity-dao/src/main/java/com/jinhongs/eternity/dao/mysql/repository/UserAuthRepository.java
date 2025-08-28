package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.UserAuth;
import com.jinhongs.eternity.dao.mysql.mapper.UserAuthMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户认证表 服务实现类
 * </p>
 */
@Component
public class UserAuthRepository extends CrudRepository<UserAuthMapper, UserAuth> {

}
