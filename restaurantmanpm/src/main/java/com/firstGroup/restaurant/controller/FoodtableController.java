package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Foodtable;
import com.firstGroup.restaurant.model.vo.UserUpdateTableVO;
import com.firstGroup.restaurant.service.ICustomerService;
import com.firstGroup.restaurant.service.IFoodtableService;
import com.firstGroup.restaurant.service.ISecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Api(tags = {"餐桌数据"})
@RestController
@RequestMapping("/foodtable")
public class FoodtableController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IFoodtableService foodtableService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ISecurityService securityService;

    /*
     * 第三次迭代接口
     */

    @ApiOperation(value = "查询分页数据")
    @GetMapping("findListByPage/{restaurantId}/{current}/{limit}")
    public Result findListByPage(
            @ApiParam(value = "餐厅ID", required = true, example = "0")
            @PathVariable String restaurantId,
            @ApiParam(value = "页码", required = true, example = "0")
            @PathVariable Integer current,
            @ApiParam(value = "每页记录数", required = true, example = "5")
            @PathVariable Integer limit) {
        Page<Foodtable> foodtablePage = new Page<>(current, limit);
        LambdaQueryWrapper<Foodtable> cond = Wrappers.lambdaQuery();
        cond.eq(Foodtable::getRId, restaurantId);
        foodtableService.page(foodtablePage, cond);
        long total = foodtablePage.getTotal();   //总记录数
        List<Foodtable> records = foodtablePage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("获取某个餐厅的餐桌总数")
    @GetMapping("/getNumInRestaurant/{restaurantId}")
    public Result getNumInRestaurant(
            @ApiParam(value = "餐厅ID" ,required = true, example = "0")
            @PathVariable String restaurantId) {
        return Result.ok().data("count", foodtableService.getNumInRestaurant(restaurantId));
    }

    @ApiOperation("获取处于不同状态的餐桌数量，状态0-未使用，1-使用中")
    @GetMapping("/getNumForEachState/{restaurantId}")
    public Result getNumForEachState(
            @ApiParam(value = "餐厅ID" ,required = true, example = "0")
            @PathVariable String restaurantId) {
        return Result.ok().data("count", foodtableService.getNumForEachState(restaurantId));
    }

    /*
     *
     * 第一次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('foodtable:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody Foodtable foodtable) {
        if (!securityService.isAccessible(foodtable))
            return Result.error().message("无权访问");
        foodtableService.add(foodtable);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('foodtable:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        Foodtable foodtable = foodtableService.findById(id);
        if (foodtable == null)
            return Result.error().message("指定的餐桌不存在");
        if (!securityService.isAccessible(foodtable))
            return Result.error().message("无权访问");
        foodtableService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('foodtable:update')")
    @ApiOperation(value = "更新（管理员专用）")
    @PutMapping("/updateByAdmin")
    public Result updateByAdmin(@RequestBody Foodtable foodtable) {
        Foodtable foodtable1 = foodtableService.findById(foodtable.getFId());
        if (foodtable1 == null)
            return Result.error().message("指定的餐桌不存在");
        if (!securityService.isAccessible(foodtable1))
            return Result.error().message("无权访问");
        foodtableService.updateData(foodtable);
        return Result.ok();
    }

    @ApiOperation("更新（用户专用）")
    @PutMapping
    public Result updateByUser(
            @ApiParam(value = "更新数据", required = true)
            @RequestBody UserUpdateTableVO tableVO) {
        Foodtable foodtable1 = foodtableService.findById(tableVO.getFId());
        if (foodtable1 == null)
            return Result.error().message("指定的餐桌不存在");
        if (!securityService.isAccessible(foodtable1))
            return Result.error().message("无权访问");
        Foodtable foodtable = new Foodtable();
        BeanUtils.copyProperties(tableVO, foodtable);
        foodtableService.updateById(foodtable);
        return Result.ok();
    }

    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Foodtable foodtable = foodtableService.findById(id);
        return Result.ok().data("foodtable",foodtable);
    }
}
