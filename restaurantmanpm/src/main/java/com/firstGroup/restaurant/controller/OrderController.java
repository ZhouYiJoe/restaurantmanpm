package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Customer;
import com.firstGroup.restaurant.model.Order;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.OrderVo;
import com.firstGroup.restaurant.model.vo.OrderVo1;
import com.firstGroup.restaurant.service.ICustomerService;
import com.firstGroup.restaurant.service.IOrderService;
import com.firstGroup.restaurant.service.IRestaurantService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Api(tags = {"订单数据"})
@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private IRestaurantService restaurantService;

    /*
     *
     * 第四次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('order:query')")
    @ApiOperation("分页查询订单，可指定排序方式、搜索关键词、日期等")
    @GetMapping("/findPage")
    public Result findPage(
            @ApiParam(value = "页码", required = true, example = "1")
            @RequestParam Integer pageId,
            @ApiParam(value = "每页记录数", required = true, example = "5")
            @RequestParam Integer pageSize,
            @ApiParam(value = "餐厅ID", required = true, example = "0")
            @RequestParam String r_id,
            @ApiParam(value = "排序字段，可选值：o_price、modify_time", example = "o_price")
            @RequestParam(required = false) String sortColumn,
            @ApiParam(value = "升序还是降序，可选值：asc、desc", example = "asc")
            @RequestParam(required = false) String sortOrder,
            @ApiParam(value = "模糊查询关键字，非必需", example = "XXX")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "起始日期", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = false) Date from,
            @ApiParam(value = "终止日期，非必需", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = false) Date to) {
        Restaurant restaurant = restaurantService.findById(r_id);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");

        List<String> columnNames = Arrays.asList("o_price", "modify_time");
        if (!columnNames.contains(sortColumn) && sortColumn != null)
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder) && sortOrder != null)
            return Result.error().message("sortOrder参数必须是asc或desc");

        Page<Order> page = (Page<Order>) orderService.findPage(
                pageId, pageSize, r_id, sortColumn, sortOrder, keyword, from, to);
        List<Order> orders = page.getRecords();
        List<Customer> customers = new ArrayList<>();   //顾客记录
        orders.forEach(order -> customers.add(customerService.findById(order.getCId())));


        return Result.ok().data("total", page.getTotal()).data("rows", orders).data("customers", customers);
    }

    @PreAuthorize("hasAuthority('order:query')")
    @ApiOperation("获取某个日期范围内的所有餐厅的总下单数量")
    @GetMapping("getNumWithinDateRange/{from}/{to}")
    public Result getNumWithinDateRange(
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
            return Result.error().message("from的日期应该在to的日期之前");
        return Result.ok().data("count", orderService.getNum(null, from, to));
    }

    @PreAuthorize("hasAuthority('order:query')")
    @ApiOperation("获取一个餐厅在某个日期范围内总下单数量")
    @GetMapping("getNumInRestaurantWithinDateRange/{restaurantId}/{from}/{to}")
    public Result getNumInRestaurantWithinDateRange(
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
            return Result.error().message("from的日期应该在to的日期之前");
        return Result.ok().data("count", orderService.getNum(restaurantId, from, to));
    }


    /*
     *
     * 第三次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('order:query')")
    @ApiOperation("获取某个餐厅的订单总数")
    @GetMapping("/getTotalInRestaurant/{restaurantId}")
    public Result getTotalInRestaurant(
            @ApiParam(value = "餐厅ID", required = true, example = "0")
            @PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        return Result.ok().data("count", orderService.getNum(restaurantId, null, null));
    }

    @PreAuthorize("hasAuthority('order:query')")
    @ApiOperation("获取所有餐厅的订单总数")
    @GetMapping("getTotal")
    public Result getTotal() {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        return Result.ok().data("count", orderService.getNum(null, null, null));
    }

    @PreAuthorize("hasAuthority('order:query')")
    @ApiOperation(value = "查询某个餐馆的订单分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rId", value = "餐馆id", dataTypeClass = String.class),
            @ApiImplicitParam(name = "current", value = "页码", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{rId}/{current}/{limit}")
    public Result findListByPage(
            @PathVariable String rId,
            @PathVariable Integer current,
            @PathVariable Integer limit) {
        Restaurant restaurant = restaurantService.findById(rId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        Page<Order> orderPage = new Page<>(current, limit);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getRId, rId);
        orderService.page(orderPage, wrapper);
        long total = orderPage.getTotal();   //总记录数
        List<Order> orders = orderPage.getRecords(); //订单记录
        List<Customer> customers = new ArrayList<>();   //顾客记录
        orders.forEach(order -> customers.add(customerService.findById(order.getCId())));

        return Result.ok().data("total", total).data("rows", orders).data("customers", customers);
    }

    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Order order = orderService.findById(id);
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        return Result.ok().data("order", order);
    }


    /*
     *
     * 第二次迭代接口
     *
     */

    @ApiOperation("获取某个顾客在某个餐馆的订单，按时间降序排序")
    @GetMapping("findCustomerOrderByRestaurantId/{customer_id}/{r_id}/{current}/{limit}")
    public Result findCustomerOrderByRestaurantId(
            @ApiParam(value = "顾客ID", example = "1238832492932147234")
            @PathVariable String customer_id,
            @ApiParam(value = "餐馆ID", example = "1238843327436247123")
            @PathVariable String r_id,
            @ApiParam(value = "页码", example = "1")
            @PathVariable Integer current,
            @ApiParam(value = "每页条数", example = "5")
            @PathVariable Integer limit) {
        Customer customer = customerService.findById(customer_id);
        if (customer == null)
            return Result.error().message("指定的顾客不存在");
        if (!securityService.isAccessible(customer))
            return Result.error().message("无权访问");
        Page<Order> orderPage = new Page<>(current, limit);
        orderService.findCustomerOrderByRestaurantId(orderPage, customer_id, r_id);
        long total = orderPage.getTotal(); //总记录数
        List<Order> records = orderPage.getRecords();//所有记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "删除订单，且把此次订单修改的库存等数据全部恢复")
    @DeleteMapping("rollbackOrder/{oId}")
    public Result rollbackOrder(@PathVariable("oId") String oId) {
        Order order = orderService.findById(oId);
        if (order == null)
            return Result.error().message("指定的订单不存在");
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");
        orderService.rollbackOrder(oId);
        return Result.ok();
    }

    @ApiOperation(value = "创建订单")
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody OrderVo orderVo) {
        Order order = new Order();
        order.setCId(orderVo.getCId());
        if (!securityService.isAccessible(order))
            return Result.error().message("无权访问");

        String oId = null;
        try {
            oId = orderService.createOrder(orderVo);
            System.out.println(oId);
        } catch (Exception o) {
            return Result.error().message(o.getMessage());
        }
        return Result.ok().data("oId", oId);
    }


    /*
     *
     * 第一次迭代接口
     *
     */
    @ApiOperation("获取某个顾客的订单，按时间降序排序")
    @GetMapping("findForCustomer/{customer_id}/")
    public Result findForCustomer(
            @ApiParam(value = "顾客ID", example = "1510632446833025026")
            @PathVariable String customer_id) {
        Customer customer = customerService.findById(customer_id);
        if (customer == null)
            return Result.error().message("指定的顾客不存在");
        if (!securityService.isAccessible(customer))
            return Result.error().message("无权访问");
        List<OrderVo1> orders = orderService.findForCustomer(customer_id);
        return Result.ok().data("orders", orders);
    }
}
