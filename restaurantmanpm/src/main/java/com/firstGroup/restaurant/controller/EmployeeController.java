package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Employee;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.service.IEmployeeService;
import com.firstGroup.restaurant.service.IRestaurantService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api(tags = {"员工数据"})
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISecurityService securityService;
    @Autowired
    private IRestaurantService restaurantService;


    /*
     *
     * 第六次迭代接口
     *
     */
    @PreAuthorize("hasAuthority('employee:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody Employee employee) {
        if (!securityService.isAccessible(employee))
            return Result.error().message("无权访问");
        employeeService.add(employee);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('employee:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody Employee employee) {
        Employee employee1 = employeeService.findById(employee.getEId());
        if (employee1 == null)
            return Result.error().message("指定的员工ID不存在");
        if (!securityService.isAccessible(employee1))
            return Result.error().message("无权访问");
        employeeService.updateData(employee);
        return Result.ok();
    }

    /*
     *
     * 第三次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('employee:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        Employee employee = employeeService.findById(id);
        if (employee == null)
            return Result.error().message("指定的员工ID不存在");
        if (!securityService.isAccessible(employee))
            return Result.error().message("无权访问");
        employeeService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('employee:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Employee employee = employeeService.findById(id);
        if (employee == null)
            return Result.error().message("指定的员工ID不存在");
        if (!securityService.isAccessible(employee))
            return Result.error().message("无权访问");
        return Result.ok().data("employee", employee);
    }

    @PreAuthorize("hasAuthority('employee:query')")
    @ApiOperation("获取所有餐厅的员工总数")
    @GetMapping("/getTotalNum")
    public Result getTotalNum() {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        return Result.ok().data("count", employeeService.getNum(null));
    }

    @PreAuthorize("hasAuthority('employee:query')")
    @ApiOperation("获取某个餐厅的员工总数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", required = true, example = "1238843327436247123", paramType =
                    "path",dataTypeClass = String.class)
    })
    @GetMapping("/getNumInRestaurant/{restaurantId}")
    public Result getNumInRestaurant(
            @PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");

        return Result.ok().data("count", employeeService.getNum(restaurantId));
    }

    @PreAuthorize("hasAuthority('employee:query')")
    @ApiOperation("获取一个餐厅各个职位的员工的数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", required = true, example = "1238843327436247123", paramType =
                    "path",dataTypeClass = String.class)
    })
    @GetMapping("/getNumForEachPosInRestaurant/{restaurantId}")
    public Result getNumForEachPosInRestaurant(
            @PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");

        return Result.ok().data("count", employeeService.getNumForEachPos(restaurantId));
    }

    @PreAuthorize("hasAuthority('employee:query')")
    @ApiOperation("获取所有餐厅各个职位的员工的数量")
    @GetMapping("/getNumForEachPos")
    public Result getNumForEachPos() {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        return Result.ok().data("count", employeeService.getNumForEachPos(null));
    }

    @PreAuthorize("hasAuthority('employee:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rId", value = "餐馆id",dataTypeClass = String.class),
            @ApiImplicitParam(name = "current", value = "页码",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limit", value = "每页条数",dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{rId}/{current}/{limit}")
    public Result findListByPage(@PathVariable String rId,
                                 @PathVariable Integer current,
                                 @PathVariable Integer limit) {
        Restaurant restaurant = restaurantService.findById(rId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getRId,rId);
        Page<Employee> categoryPage = new Page<>(current, limit);
        employeeService.page(categoryPage, wrapper);
        long total = categoryPage.getTotal();   //总记录数
        List<Employee> records = categoryPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    /*
     *
     * 第一次迭代接口
     *
     */

}
