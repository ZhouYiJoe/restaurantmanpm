package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Advice;
import com.firstGroup.restaurant.model.Customer;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.AdviceVo;
import com.firstGroup.restaurant.service.*;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Api(tags = "投诉与建议")
@RequestMapping("/advice")
@RestController
public class AdviceController {

    @Autowired
    private IAdviceService adviceService;
    @Autowired
    private ISecurityService securityService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ICustomerService customerService;

    /*
     *
     * 第六次迭代接口
     *
     */

    @ApiOperation("通过顾客id分页查询建议")
    @GetMapping("/findPageByCustomerId")
    public Result findPageByCustomerId(
            @ApiParam(name = "pageId", value = "页码", example = "1")
            @RequestParam(required = true) Integer pageId,
            @ApiParam(name = "pageSize", value = "每页条数", example = "5")
            @RequestParam(required = true) Integer pageSize,
            @ApiParam(name = "r_id", value = "餐馆ID", example = "1238843327436247123")
            @RequestParam(required = true) String r_id,
            @ApiParam(name = "customerId", value = "顾客ID", example = "1238832492932147234")
            @RequestParam(required = true) String customerId) {
        Restaurant restaurant = restaurantService.findById(r_id);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        Customer customer = customerService.findById(customerId);
        if (customer == null)
            return Result.error().message("指定的顾客不存在");
        if (!securityService.isAccessible(customer))
            return Result.error().message("无权访问");
        return adviceService.findByCustomerId(pageId, pageSize, r_id, customerId);
    }

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('advice:query')")
    @ApiOperation("分页查询建议，可指定排序方式等")
    @GetMapping("/findPage")
    public Result findPage(
            @ApiParam(name = "pageId", value = "页码", example = "1")
            @RequestParam(required = true) Integer pageId,
            @ApiParam(name = "pageSize", value = "每页条数", example = "5")
            @RequestParam(required = true) Integer pageSize,
            @ApiParam(name = "r_id", value = "餐馆ID", example = "1238843327436247123")
            @RequestParam(required = false) String r_id,
            @ApiParam(name = "sortColumn", value = "排序字段：name, star, status, o_id, create_time", example = "create_time")
            @RequestParam(required = false) String sortColumn,
            @ApiParam(name = "sortOrder", value = "排序规则", example = "asc")
            @RequestParam(required = false) String sortOrder,
            @ApiParam(name = "status", value = "是否已处理，0为未处理，1为已处理，不指定则均查询", example = "0")
            @RequestParam(required = false) Integer status,
            @ApiParam(name = "keyword", value = "关键词,可以按照建议内容，建议类别，顾客姓名查询", example = "XXX")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "起始日期，非必需", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = false) Date from,
            @ApiParam(value = "终止日期，非必需", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = false) Date to) {
        if (r_id != null) {
            Restaurant restaurant = restaurantService.findById(r_id);
            if (restaurant == null)
                return Result.error().message("指定的餐厅不存在");
            if (!securityService.isAccessible(restaurant))
                return Result.error().message("无权访问");
        } else {
            AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
            if (userDetails.getUserType() != UserType.SUPER_ADMIN)
                return Result.error().message("无权访问");
        }
        List<String> columnNames = Arrays.asList("name", "create_time", "star", "status", "o_id");
        if (!columnNames.contains(sortColumn) && sortColumn != null)
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder) && sortOrder != null)
            return Result.error().message("sortOrder参数必须是asc或desc");
        return adviceService.findPage(pageId, pageSize, r_id, sortColumn, sortOrder, status, keyword, from, to);
    }

    @ApiOperation("分页查询大于等于指定星级的数量")
    @GetMapping("/findNumberByDate")
    public Result findNumberByDate(
            @ApiParam(value = "起始日期，非必需", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = true) Date from,
            @ApiParam(value = "终止日期，非必需", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = true) Date to,
            @ApiParam(name = "r_id", value = "餐馆ID", example = "1238843327436247123")
            @RequestParam(required = false) String r_id,
            @ApiParam(name = "star", value = "大于等于指定星级", example = "1")
            @RequestParam(required = false) Integer star) {
        if (from.after(to))
            return Result.error().message("from的日期应该在to的日期之前");
        return adviceService.findNumberByDate(from, to, star, r_id);
    }

    @PreAuthorize("hasAuthority('advice:update')")
    @ApiOperation("修改建议状态")
    @PutMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(
            @ApiParam(value = "建议ID", required = true)
            @PathVariable String id,
            @ApiParam(value = "状态, 0为未处理，1为已处理", required = true, example = "0")
            @PathVariable Integer status) {
        Advice advice = adviceService.findById(id);
        if (advice == null)
            return Result.error().message("指定的建议不存在");
        if (!securityService.isAccessible(advice))
            return Result.error().message("无权访问");
        adviceService.updateStatus(id, status);
        return Result.ok();
    }

    /*
     *
     * 第二次迭代接口
     *
     */

    @PostMapping
    @ApiOperation("提交建议")
    public Result add(
            @ApiParam(value = "被提交的建议", required = true)
            @RequestBody AdviceVo adviceVo) {
        Advice advice = new Advice();
        BeanUtils.copyProperties(adviceVo,advice);
        if (!securityService.isAccessible(advice))
            return Result.error().message("无权访问");
        adviceService.add(advice);
        return Result.ok();
    }

    @GetMapping("/findAdviceByPage/{current}/{limit}")
    @ApiOperation("分页查询，按提交时间倒序排序")
    public Result findByPage(
            @ApiParam(value = "当前页码", example = "0", required = true)
            @PathVariable Integer current,
            @ApiParam(value = "每页记录数", example = "5", required = true)
            @PathVariable Integer limit) {
        return null;
    }

    @ApiOperation("分页查询某个顾客提交过的建议，按提交时间倒序排序")
    @GetMapping("/findAdviceFromCustomer/{cId}")
    public Result findFromCustomer(
            @ApiParam(value = "顾客ID", required = true)
            @PathVariable String cId) {
        return null;
    }

    @ApiOperation("删除建议")
    @DeleteMapping("/{id}")
    public Result delete(
            @ApiParam(value = "建议ID", required = true)
            @PathVariable String id) {
        return null;
    }

    @ApiOperation("修改建议")
    @PutMapping
    public Result update(
            @ApiParam(value = "修改后的建议", required = true)
            @RequestBody Advice advice) {
        return null;
    }
}
