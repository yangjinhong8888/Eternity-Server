package com.jinhongs.eternity.dao.mysql.repository;

import com.jinhongs.eternity.model.entity.ApiPermission;
import com.jinhongs.eternity.dao.mysql.mapper.ApiPermissionMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Component
public class ApiPermissionRepository extends CrudRepository<ApiPermissionMapper, ApiPermission> {

}
