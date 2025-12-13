package com.jinhongs.eternity.dao.mysql.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.jinhongs.eternity.common.exception.ClientException;
import com.jinhongs.eternity.dao.mysql.mapper.*;
import com.jinhongs.eternity.dao.mysql.model.dto.UserEntity;
import com.jinhongs.eternity.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Component
@RequiredArgsConstructor
public class UserInfoRepository extends CrudRepository<UserInfoMapper, UserInfo> {

    private final UserInfoMapper userInfoMapper;

    private final UserRoleMapper userRoleMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final PermissionMapper permissionMapper;

    private final UserAuthMapper userAuthMapper;

    public UserEntity findUserIdByUserName(String username) {
        UserAuth userAuth = this.userAuthMapper.selectOne(
                new LambdaQueryWrapper<UserAuth>().eq(UserAuth::getIdentifier, username)
        );
        UserInfo userInfo;
        if (userAuth != null) {
            userInfo = userInfoMapper.selectOne(
                    new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userAuth.getUserId())
            );
        } else {
            throw new ClientException("用户不存在");
        }
        if (userInfo == null) {
            throw new ClientException("用户不存在");
        }

        // 获取用户权限
        // 根据用户ID获取角色ID列表
        List<Long> roleIds = userRoleMapper.selectList(
                        new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userAuth.getUserId())
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

        return new UserEntity(
                userInfo.getId(),
                userAuth.getIdentifier(),
                userAuth.getCredential(),
                permKeys
        );
    }

    public boolean isExistUsername(String username) {
        return userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, username)) != null;
    }

}
