package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.service.IRestaurantService;
import com.firstGroup.restaurant.service.ISecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"餐厅数据"})
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第三次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('restaurant:query')")
    @ApiOperation("获取不同地区的餐厅分布数量")
    @GetMapping("/getNumInEachArea")
    public Result getNumInEachArea() {
        return Result.ok().data("count", restaurantService.getNumInEachArea());
    }

    @PreAuthorize("hasAuthority('restaurant:query')")
    @ApiOperation("获取餐厅的总数量")
    @GetMapping("/getTotalNum")
    public Result getTotalNum() {
        return Result.ok().data("count", restaurantService.getTotalNum());
    }

    /*
     *
     * 第二次迭代接口
     *
     */
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Restaurant restaurant = restaurantService.findById(id);
        return Result.ok().data("restaurant",restaurant);
    }

    @ApiOperation(value = "查询所有数据")
    @GetMapping("findAll")
    public Result findListByPage() {
        List<Restaurant> records = restaurantService.findAll();
        return Result.ok().data("rows", records);
    }

    /*
     *
     * 第一次迭代接口
     *
     */

    @ApiOperation(value = "新增")
    @PostMapping()
    @PreAuthorize("hasAuthority('restaurant:add')")
    public Result add(@RequestBody Restaurant restaurant) {
        restaurantService.add(restaurant);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('restaurant:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        restaurantService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('restaurant:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody Restaurant restaurant) {
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        restaurantService.updateData(restaurant);
        return Result.ok();
    }
}
