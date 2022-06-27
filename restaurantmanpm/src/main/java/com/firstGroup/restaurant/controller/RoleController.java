package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.AdminRole;
import com.firstGroup.restaurant.model.Role;
import com.firstGroup.restaurant.service.IAdminRoleService;
import com.firstGroup.restaurant.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {
    @Autowired
    private IAdminRoleService adminRoleService;
    @Autowired
    private IRoleService roleService;

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('role:query')")
    @ApiOperation("查询管理员拥有的所有角色")
    @GetMapping("/findForAdmin/{adminId}")
    public Result findForAdmin(
            @ApiParam(value = "管理员编号", required = true, example = "0")
            @PathVariable String adminId) {
        QueryWrapper<AdminRole> cond = new QueryWrapper<>();
        cond.eq("admin_id", adminId).select("role_id");
        List<AdminRole> adminRoles = adminRoleService.list(cond);
        List<String> roleIdList = adminRoles
                .stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        if (roleIdList.isEmpty()) return Result.ok().data("roles", Collections.emptyList());
        return Result.ok().data("roles", roleService.listByIds(roleIdList));
    }

    @PreAuthorize("hasAuthority('role:update')")
    @PutMapping
    @ApiOperation("更新")
    public Result update(
            @ApiParam(value = "角色", required = true)
            @RequestBody Role role) {
        if (roleService.updateById(role)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('role:add')")
    @ApiOperation("新增")
    @PostMapping
    public Result add(
            @ApiParam(value = "角色", required = true)
            @RequestBody Role role) {
        if (roleService.save(role)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('role:delete')")
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public Result delete(
            @ApiParam(value = "角色编号", required = true, example = "0")
            @PathVariable String id) {
        if (roleService.removeById(id)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('role:query')")
    @ApiOperation("查询所有角色")
    @GetMapping
    public Result findAll(
            @ApiParam(value = "查询关键字", example = "XXX")
            @RequestParam(required = false) String keyword) {
        System.out.println(keyword);
        QueryWrapper<Role> cond = new QueryWrapper<>();
        if (keyword != null) cond.like("name", keyword);
        return Result.ok().data("roles", roleService.list(cond));
    }
}
