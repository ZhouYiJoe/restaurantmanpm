package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.MenuOrder;
import com.firstGroup.restaurant.model.Order;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.OrderDishVo;
import com.firstGroup.restaurant.service.IMenuOrderService;
import com.firstGroup.restaurant.service.IOrderService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = {"订单-菜品关系数据"})
@RestController
@RequestMapping("/menu-order")
public class MenuOrderController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMenuOrderService menuOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第二次迭代接口
     *
     */

    @ApiOperation("获取一个订单所包含的所有菜品")
    @GetMapping("findForOrder/{oId}")
    public Result findForOrder(
            @ApiParam(value = "oId", example = "0")
            @PathVariable String oId) {
        Order order = orderService.findById(oId);
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        List<OrderDishVo> orderDishes = menuOrderService.findForOrder(oId);
        return Result.ok().data("orderDishes", orderDishes);
    }

    /*
     *
     * 第一次迭代接口
     *
     */


    @ApiOperation("查找单个")
    @GetMapping("/{oId}/{dId}")
    public Result findById(
            @ApiParam(value = "订单ID", example = "0")
            @PathVariable String oId,
            @ApiParam(value = "菜品ID", example = "0")
            @PathVariable String dId) {
        Order order = orderService.findById(oId);
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        return Result.ok().data("menuOrder", menuOrderService.findById(oId, dId));
    }

    @ApiOperation("删除")
    @DeleteMapping("/{oId}/{dId}")
    public Result delete(
            @ApiParam(value = "订单ID", example = "0")
            @PathVariable String oId,
            @ApiParam(value = "菜品ID", example = "0")
            @PathVariable String dId) {
        Order order = orderService.findById(oId);
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        menuOrderService.delete(oId, dId);
        return Result.ok();
    }

    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody MenuOrder menuOrder) {
        Order order = orderService.findById(menuOrder.getOId());
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        menuOrderService.add(menuOrder);
        return Result.ok();
    }

    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody MenuOrder menuOrder) {
        Order order = orderService.findById(menuOrder.getOId());
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        menuOrderService.updateData(menuOrder);
        return Result.ok();
    }
}
