package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Customer;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.CustomerVo;
import com.firstGroup.restaurant.model.vo.UserUpdateCusVO;
import com.firstGroup.restaurant.service.ICustomerService;
import com.firstGroup.restaurant.service.IRedisService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.JsonUtil;
import com.firstGroup.restaurant.utils.JwtUtil;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"顾客数据"})
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第五次迭代接口
     *
     */
    @ApiOperation(value = "微信授权登录后，根据登录信息返回顾客id")
    @PostMapping("getCustomerId")
    public Result getCustomerId(@RequestHeader("token") String token,
                                @RequestBody CustomerVo customerVo) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.CUSTOMER)
            return Result.error().message("无权访问");
        //把顾客的ID保存到Redis中的登录信息里
        String userId = JwtUtil.getUserId(token);
        String cId = customerService.getCustomerId(customerVo);
        String key = IRedisService.concatKey("user_details", userId);
        userDetails.setUsername(cId);
        redisService.put(key, "user_details", JsonUtil.toJson(userDetails));
        return Result.ok().data("cId", cId);
    }

    /*
     *
     * 第一次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('customer:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageCount", value = "每页条数",dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{current}/{limit}")
    public Result findListByPage(@PathVariable Integer current,
                                 @PathVariable Integer limit) {
        Page<Customer> customerPage = new Page<>(current, limit);
        customerService.page(customerPage, null);
        long total = customerPage.getTotal();   //总记录数
        List<Customer> records = customerPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('customer:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody Customer customer) {
        customerService.add(customer);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('customer:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        customerService.delete(id);
        return Result.ok();
    }

    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody UserUpdateCusVO customerVO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerVO, customer);
        if (!securityService.isAccessible(customer))
            return Result.error().message("无权访问");
        customerService.updateData(customer);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('customer:update')")
    @ApiOperation("管理员专用更新接口")
    @PutMapping("/updateByAdmin")
    public Result updateByAdmin(@RequestBody Customer customer) {
        customerService.updateData(customer);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('customer:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Customer customer = customerService.findById(id);
        if (!securityService.isAccessible(customer))
            return Result.error().message("无权访问");
        return Result.ok().data("customer", customer);
    }

}
