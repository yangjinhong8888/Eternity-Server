package com.jinhongs.eternity.service.service.impl;

import com.jinhongs.eternity.common.exception.ClientException;
import com.jinhongs.eternity.dao.mysql.repository.RoleRepository;
import com.jinhongs.eternity.model.entity.Role;
import com.jinhongs.eternity.service.model.dto.RoleCreateDTO;
import com.jinhongs.eternity.service.model.dto.RoleUpdateDTO;
import com.jinhongs.eternity.service.model.vo.RoleVO;
import com.jinhongs.eternity.service.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Long createRole(RoleCreateDTO dto) {
        Role role = new Role();
        role.setRoleName(dto.getRoleName());
        role.setRoleKey(dto.getRoleKey());
        role.setIsEnable((byte) 1);
        roleRepository.save(role);
        return role.getId();
    }

    @Override
    public void updateRole(RoleUpdateDTO dto) {
        Role role = roleRepository.getById(dto.getId());
        if (role == null) throw new ClientException("角色不存在");
        role.setRoleName(dto.getRoleName());
        role.setIsEnable(dto.getIsEnable());
        roleRepository.updateById(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.removeById(id);
    }

    @Override
    public List<RoleVO> listRoles() {
        return roleRepository.list().stream().map(role -> {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
            return vo;
        }).toList();
    }
}
