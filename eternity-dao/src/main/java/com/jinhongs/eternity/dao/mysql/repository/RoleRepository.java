package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.Role;
import com.jinhongs.eternity.dao.mysql.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 */
@Component
public class RoleRepository extends CrudRepository<RoleMapper, Role> {

}
