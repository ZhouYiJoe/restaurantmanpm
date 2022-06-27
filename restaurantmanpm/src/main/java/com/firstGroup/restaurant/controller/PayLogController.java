package com.firstGroup.restaurant.controller;

/**
 * @ClassName PayLogController
 * @Description TODO
 * @Author wuhaojie
 * @Date 2021/12/22 10:27
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Order;
import com.firstGroup.restaurant.service.IOrderService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.service.impl.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"微信支付"})
@RestController
@RequestMapping({"/paylog"})
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第六次迭代接口
     *
     */
    @ApiOperation("根据订单号请求微信支付二维码")
    @GetMapping({"createNative/{orderNo}/{note}"})
    public Result createNative(
            @ApiParam(value = "订单号", example = "2022031923294916248")
            @PathVariable String orderNo,
            @ApiParam(value = "备注", example = "不要辣")
            @PathVariable String note) {
        Order order = orderService.findById(orderNo);
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        Map<String, Object> map = payLogService.createNative(orderNo, note);
        return Result.ok().data(map);
    }

    /*
     *
     * 第二次迭代接口
     *
     */

    @ApiOperation("根据订单号查询订单支付状态，并更改订单支付状态")
    @GetMapping({"queryPayStatus/{orderNo}"})
    public Result queryPayStatus(
            @ApiParam(value = "订单号", example = "2022031923294916248")
            @PathVariable String orderNo) {
        Order order = orderService.findById(orderNo);
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return Result.error().message("支付出错了！");
        } else if ((map.get("trade_state")).equals("SUCCESS")) {
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功！");
        } else {
            return Result.ok().code(25000).message("支付中...");
        }
    }
}
