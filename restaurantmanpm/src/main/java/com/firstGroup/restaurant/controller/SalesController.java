package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.service.IOrderService;
import com.firstGroup.restaurant.service.IRestaurantService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/sales")
@Api(tags = "销量统计")
public class SalesController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第三次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('sales:query')")
    @ApiOperation("计算一个餐厅在某个时间段内的销售额")
    @GetMapping("/getInRestaurantWithinDateRange/{restaurantId}/{from}/{to}")
    public Result getInRestaurantWithinDateRange(
            @ApiParam(value = "餐厅ID", required = true, example = "0")
            @PathVariable String restaurantId,
            @ApiParam(value = "起始日期", required = true, example = "2022-04-16")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable Date from,
            @ApiParam(value = "终止日期", required = true, example = "2022-04-17")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable Date to) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        if (from.after(to))
            return Result.error().message("起始日期不能在终止日期之后");
        return Result.ok().data("sales",
                orderService.getSum(restaurantId, from, to));
    }

    @PreAuthorize("hasAuthority('sales:query')")
    @ApiOperation("计算一个餐厅的总销售额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "餐厅ID", dataTypeClass = String.class),
    })
    @GetMapping("/getTotalInRestaurant/{restaurantId}")
    public Result getTotalInRestaurant(@PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        return Result.ok().data("sales", orderService.getSum(restaurantId, null, null));
    }

    @PreAuthorize("hasAuthority('sales:query')")
    @ApiOperation("计算所有餐厅在某个时间段内的总销售额")
    @GetMapping("/getWithinDateRange/{from}/{to}")
    public Result getWithinDateRange(
            @ApiParam(value = "起始日期", required = true, example = "2022-04-16")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable Date from,
            @ApiParam(value = "终止日期", required = true, example = "2022-04-17")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @PathVariable Date to) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        if (from.after(to))
            return Result.error().message("起始日期不能在终止日期之后");
        return Result.ok().data("sales", orderService.getSum(null, from, to));
    }

    @PreAuthorize("hasAuthority('sales:query')")
    @ApiOperation("计算所有餐厅的总销售额")
    @GetMapping("/getTotal")
    public Result getTotal() {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        return Result.ok().data("sales", orderService.getSum(null, null, null));
    }
}
