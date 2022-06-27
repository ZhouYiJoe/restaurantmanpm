package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.AdminRole;
import com.firstGroup.restaurant.service.IAdminRoleService;
import com.firstGroup.restaurant.service.IAdminService;
import com.firstGroup.restaurant.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminRole")
@Api(tags = "管理员-角色关系管理")
public class AdminRoleController {
    @Autowired
    private IAdminRoleService adminRoleService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('admin_role:add')")
    @ApiOperation("新增")
    @PostMapping
    public Result add(
            @ApiParam(value = "管理员-角色关系", required = true)
            @RequestBody AdminRole adminRole) {
        if (!adminService.exists(adminRole.getAdminId()))
            return Result.error().message("指定的管理员不存在");
        if (!roleService.exists(adminRole.getRoleId()))
            return Result.error().message("指定的角色不存在");
        if (adminRoleService.save(adminRole)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('admin_role:delete')")
    @ApiOperation("删除")
    @DeleteMapping("/{adminId}/{roleId}")
    public Result delete(
            @ApiParam(value = "管理员编号", required = true, example = "0")
            @PathVariable String adminId,
            @ApiParam(value = "角色编号", required = true, example = "0")
            @PathVariable String roleId) {
        QueryWrapper<AdminRole> cond = new QueryWrapper<>();
        cond.eq("admin_id", adminId).eq("role_id", roleId);
        if (adminRoleService.remove(cond)) return Result.ok();
        return Result.error();
    }
}
