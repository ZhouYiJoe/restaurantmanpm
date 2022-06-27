package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.DishMc;
import com.firstGroup.restaurant.service.IDishMcService;
import com.firstGroup.restaurant.service.IDishService;
import com.firstGroup.restaurant.service.ISecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(tags = {"菜品-材料关系数据"})
@RestController
@RequestMapping("/dish-mc")
public class DishMcController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDishMcService dishMcService;
    @Autowired
    private IDishService dishService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第四次迭代接口
     *
     */
    @ApiOperation("获取所有菜品所含有的材料")
    @GetMapping("/findAllForDish/{r_id}")
    public Result findAllForDish(
            @ApiParam(value = "餐馆ID", example = "1238843327436247123")
            @PathVariable String r_id) {
        return Result.ok().data("data", dishMcService.findAllForDish(r_id));
    }


    /*
     *
     * 第一次迭代接口
     *
     */

    @ApiOperation("获取一个菜品所含有的材料")
    @GetMapping(params = "d_id")
    public List<DishMc> findForDish(
            @ApiParam(value = "菜品ID", example = "0")
            @RequestParam("d_id") String dId) {
        return dishMcService.findForDish(dId);
    }

    @PreAuthorize("hasAuthority('dish_mc:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody DishMc dishMc) {
        Dish dish = dishService.getById(dishMc.getDId());
        if (dish == null)
            return Result.error().message("指定的菜品不存在");
        if (!securityService.isAccessible(dish))
            return Result.error().message("无权访问");
        dishMcService.add(dishMc);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('dish_mc:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{mcId}/{dId}")
    public Result delete(
            @ApiParam(value = "材料ID", example = "0")
            @PathVariable String mcId,
            @ApiParam(value = "菜品ID", example = "0")
            @PathVariable String dId) {
        Dish dish = dishService.getById(dId);
        if (dish == null)
            return Result.error().message("指定的菜品不存在");
        if (!securityService.isAccessible(dish))
            return Result.error().message("无权访问");
        dishMcService.delete(mcId, dId);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('dish_mc:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody DishMc dishMc) {
        Dish dish = dishService.getById(dishMc.getDId());
        if (dish == null)
            return Result.error().message("指定的菜品不存在");
        if (!securityService.isAccessible(dish))
            return Result.error().message("无权访问");
        dishMcService.updateData(dishMc);
        return Result.ok();
    }

    @ApiOperation(value = "查询单个")
    @GetMapping("{mcId}/{dId}")
    public DishMc findById(
            @ApiParam(value = "材料ID", example = "0")
            @PathVariable String mcId,
            @ApiParam(value = "菜品ID", example = "0")
            @PathVariable String dId) {
        return dishMcService.findById(mcId, dId);
    }

}
