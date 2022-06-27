package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.MenuRole;
import com.firstGroup.restaurant.model.Role;
import com.firstGroup.restaurant.service.IMenuRoleService;
import com.firstGroup.restaurant.service.IMenuService;
import com.firstGroup.restaurant.service.IRoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menuRole")
@Api(tags = "权限-角色关系管理")
public class MenuRoleController {
    @Autowired
    private IMenuRoleService menuRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('menu_role:add')")
    @ApiOperation("新增")
    @PostMapping
    public Result add(
            @ApiParam(value = "权限-角色关系", required = true)
            @RequestBody MenuRole menuRole) {
        LambdaQueryWrapper<Role> cond = new LambdaQueryWrapper<>();
        cond.eq(Role::getId, menuRole.getRoleId());
        if (!roleService.exists(menuRole.getRoleId()))
            return Result.error().message("指定的角色不存在");
        if (!menuService.exists(menuRole.getMenuId()))
            return Result.error().message("指定的权限不存在");
        if (menuRoleService.save(menuRole)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('menu_role:delete')")
    @ApiOperation("删除")
    @DeleteMapping("/{menuId}/{roleId}")
    public Result delete(
            @ApiParam(value = "权限编号", required = true, example = "0")
            @PathVariable String menuId,
            @ApiParam(value = "角色编号", required = true, example = "0")
            @PathVariable String roleId) {
        System.out.println(menuId);
        System.out.println(roleId);
        QueryWrapper<MenuRole> cond = new QueryWrapper<>();
        cond.eq("menu_id", menuId).eq("role_id", roleId);
        if (menuRoleService.remove(cond)) return Result.ok();
        return Result.error();
    }
}
