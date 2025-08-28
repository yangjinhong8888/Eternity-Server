package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.RolePermission;
import com.jinhongs.eternity.dao.mysql.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 */
@Component
public class RolePermissionRepository extends CrudRepository<RolePermissionMapper, RolePermission> {

}
