package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.UserRole;
import com.jinhongs.eternity.dao.mysql.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 */
@Component
public class UserRoleRepository extends CrudRepository<UserRoleMapper, UserRole> {

}
