package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Menu;
import com.firstGroup.restaurant.model.MenuRole;
import com.firstGroup.restaurant.service.IMenuRoleService;
import com.firstGroup.restaurant.service.IMenuService;
import com.firstGroup.restaurant.service.IMenuTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
@Api(tags = "权限管理")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;
    @Autowired
    private IMenuTypeService menuTypeService;

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('menu:update')")
    @PutMapping
    @ApiOperation("更新")
    public Result update(
            @ApiParam(value = "权限", required = true)
            @RequestBody Menu menu) {
        if (!menuTypeService.exists(menu.getTypeId()))
            return Result.error().message("指定的权限类型不存在");
        if (menuService.updateById(menu)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('menu:add')")
    @ApiOperation("新增")
    @PostMapping
    public Result add(
            @ApiParam(value = "权限", required = true)
            @RequestBody Menu menu) {
        if (!menuTypeService.exists(menu.getTypeId()))
            return Result.error().message("指定的权限类型不存在");
        if (menuService.save(menu)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('menu:delete')")
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public Result delete(
            @ApiParam(value = "权限编号", required = true, example = "0")
            @PathVariable String id) {
        if (menuService.removeById(id)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('menu:query')")
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result findPage(
            @ApiParam(value = "页号", required = true, example = "0")
            @RequestParam @Min(0) Long pageId,
            @ApiParam(value = "每页记录数", required = true, example = "5")
            @RequestParam @Min(1) Long pageSize,
            @ApiParam(value = "查询关键字", example = "XXX")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "类型编号", example = "0")
            @RequestParam(required = false) String typeId) {
        LambdaQueryWrapper<Menu> cond = new LambdaQueryWrapper<>();
        if (keyword != null) cond.like(Menu::getName, keyword);
        if (typeId != null) cond.eq(Menu::getTypeId, typeId);
        return Result.ok().data("page",
                menuService.page(new Page<>(pageId, pageSize), cond));
    }

    @PreAuthorize("hasAuthority('menu:query')")
    @ApiOperation("查询角色拥有的所有权限")
    @GetMapping("/findForRole/{roleId}")
    public Result findForRole(
            @ApiParam(value = "角色编号", required = true, example = "0")
            @PathVariable String roleId) {
        QueryWrapper<MenuRole> cond = new QueryWrapper<>();
        cond.eq("role_id", roleId).select("menu_id");
        List<MenuRole> menuRoles = menuRoleService.list(cond);
        List<String> menuIdList = menuRoles
                .stream().map(MenuRole::getMenuId).collect(Collectors.toList());
        if (menuIdList.isEmpty()) return Result.ok().data("menus", Collections.emptyList());
        return Result.ok().data("menus", menuService.listByIds(menuIdList));
    }
}
