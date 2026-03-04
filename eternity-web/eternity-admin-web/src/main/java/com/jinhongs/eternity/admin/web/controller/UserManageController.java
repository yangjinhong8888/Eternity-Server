package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.utils.ResultUtils;
import com.jinhongs.eternity.common.utils.result.PageResult;
import com.jinhongs.eternity.common.utils.result.Result;
import com.jinhongs.eternity.service.model.vo.UserVO;
import com.jinhongs.eternity.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理接口")
@RequiredArgsConstructor
public class UserManageController {

    private final UserService userService;

    @Operation(summary = "分页查询用户列表")
    @GetMapping("/list")
    public ResponseEntity<Result<PageResult<UserVO>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResultUtils.ok(userService.listUsers(page, size));
    }

    @Operation(summary = "分配角色")
    @PutMapping("/assign-roles")
    public ResponseEntity<Result<Void>> assignRoles(
            @RequestParam Long userId,
            @RequestBody List<Long> roleIds) {
        userService.assignRoles(userId, roleIds);
        return ResultUtils.ok();
    }
}
