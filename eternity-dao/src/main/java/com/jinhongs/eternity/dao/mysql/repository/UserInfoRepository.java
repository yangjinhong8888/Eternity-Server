package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.UserInfo;
import com.jinhongs.eternity.dao.mysql.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Component
public class UserInfoRepository extends CrudRepository<UserInfoMapper, UserInfo> {

}
