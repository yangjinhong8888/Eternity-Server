package com.jinhongs.eternity.dao.mysql.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinhongs.eternity.dao.mysql.mapper.PermissionMapper;
import com.jinhongs.eternity.dao.mysql.mapper.RolePermissionMapper;
import com.jinhongs.eternity.dao.mysql.mapper.UserRoleMapper;
import com.jinhongs.eternity.model.entity.Permission;
import com.jinhongs.eternity.model.entity.RolePermission;
import com.jinhongs.eternity.model.entity.UserInfo;
import com.jinhongs.eternity.dao.mysql.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.jinhongs.eternity.model.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Component
public class UserInfoRepository extends CrudRepository<UserInfoMapper, UserInfo> {

    private final UserRoleMapper userRoleMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final PermissionMapper permissionMapper;

    @Autowired
    public UserInfoRepository(UserRoleMapper userRoleMapper, RolePermissionMapper rolePermissionMapper, PermissionMapper permissionMapper) {
        this.userRoleMapper = userRoleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.permissionMapper = permissionMapper;
    }

    public List<String> findUserPermKeyBuId(Long userId) {
        // 获取用户权限
        // 根据用户ID获取角色ID列表
        List<Long> roleIds = userRoleMapper.selectList(
                new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId)
            ).stream()
            .map(UserRole::getRoleId)
            .toList();

        // 根据角色ID列表获取权限ID列表
        List<Long> permissionIds = new ArrayList<>();
        if (!roleIds.isEmpty()) {
            permissionIds = rolePermissionMapper.selectList(
                    new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIds)
                ).stream()
                .map(RolePermission::getPermissionId)
                .toList();
        }

        // 根据权限ID列表获取权限permKey
        List<String> permKeys = new ArrayList<>();
        if (!permissionIds.isEmpty()) {
            permKeys = permissionMapper.selectList(
                    new LambdaQueryWrapper<Permission>().in(Permission::getId, permissionIds)
                ).stream()
                .map(Permission::getPermKey)
                .toList();
        }

        return permKeys;
    }

}
