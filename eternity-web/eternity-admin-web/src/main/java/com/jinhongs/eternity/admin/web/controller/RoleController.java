package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.model.dto.params.RoleCreateParams;
import com.jinhongs.eternity.admin.web.model.dto.params.RoleUpdateParams;
import com.jinhongs.eternity.admin.web.utils.ResultUtils;
import com.jinhongs.eternity.common.utils.result.Result;
import com.jinhongs.eternity.service.model.dto.RoleCreateDTO;
import com.jinhongs.eternity.service.model.dto.RoleUpdateDTO;
import com.jinhongs.eternity.service.model.vo.RoleVO;
import com.jinhongs.eternity.service.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Tag(name = "角色管理接口")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "创建角色")
    @PostMapping("/create")
    public ResponseEntity<Result<Long>> create(@Valid @RequestBody RoleCreateParams params) {
        RoleCreateDTO dto = new RoleCreateDTO();
        BeanUtils.copyProperties(params, dto);
        return ResultUtils.ok(roleService.createRole(dto));
    }

    @Operation(summary = "更新角色")
    @PutMapping("/update")
    public ResponseEntity<Result<Void>> update(@Valid @RequestBody RoleUpdateParams params) {
        RoleUpdateDTO dto = new RoleUpdateDTO();
        BeanUtils.copyProperties(params, dto);
        roleService.updateRole(dto);
        return ResultUtils.ok();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/delete")
    public ResponseEntity<Result<Void>> delete(@RequestParam Long id) {
        roleService.deleteRole(id);
        return ResultUtils.ok();
    }

    @Operation(summary = "查询所有角色")
    @GetMapping("/list")
    public ResponseEntity<Result<List<RoleVO>>> list() {
        return ResultUtils.ok(roleService.listRoles());
    }
}
