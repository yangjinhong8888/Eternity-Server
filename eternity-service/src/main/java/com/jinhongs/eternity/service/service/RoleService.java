package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.service.model.dto.RoleCreateDTO;
import com.jinhongs.eternity.service.model.dto.RoleUpdateDTO;
import com.jinhongs.eternity.service.model.vo.RoleVO;

import java.util.List;

public interface RoleService {
    Long createRole(RoleCreateDTO dto);
    void updateRole(RoleUpdateDTO dto);
    void deleteRole(Long id);
    List<RoleVO> listRoles();
}
