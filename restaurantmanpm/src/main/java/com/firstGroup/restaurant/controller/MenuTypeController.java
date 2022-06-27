package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.MenuType;
import com.firstGroup.restaurant.service.IMenuTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menuType")
@Api(tags = "权限类型管理")
public class MenuTypeController {
    @Autowired
    private IMenuTypeService menuTypeService;

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('menu_type:query')")
    @GetMapping("/{id}")
    @ApiOperation("ID查询")
    public Result selectById(
            @ApiParam(value = "权限类型ID", required = true, example = "0")
            @PathVariable String id) {
        return Result.ok().data("menuType", menuTypeService.getById(id));
    }

    @PreAuthorize("hasAuthority('menu_type:update')")
    @PutMapping
    @ApiOperation("更新")
    public Result update(
            @ApiParam(value = "权限类型", required = true)
            @RequestBody MenuType menuType) {
        if (menuTypeService.updateById(menuType)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('menu_type:add')")
    @ApiOperation("新增")
    @PostMapping
    public Result add(
            @ApiParam(value = "权限类型", required = true)
            @RequestBody MenuType menuType) {
        if (menuTypeService.save(menuType)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('menu_type:delete')")
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public Result delete(
            @ApiParam(value = "权限类型编号", required = true, example = "0")
            @PathVariable String id) {
        if (menuTypeService.removeById(id)) return Result.ok();
        return Result.error();
    }

    @PreAuthorize("hasAuthority('menu_type:query')")
    @ApiOperation("获取所有权限类别信息")
    @GetMapping
    public Result findAll() {
        return Result.ok().data("menuTypes", menuTypeService.list());
    }
}
