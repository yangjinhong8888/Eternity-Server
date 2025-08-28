package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.Permission;
import com.jinhongs.eternity.dao.mysql.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 */
@Component
public class PermissionRepository extends CrudRepository<PermissionMapper, Permission> {

}
