package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.model.Stock;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.StockVo;
import com.firstGroup.restaurant.service.IRestaurantService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.service.IStockService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Api(tags = {"库存数据"})
@RestController
@RequestMapping("/stock")
public class StockController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStockService stockService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第五次迭代接口
     *
     */
    @PreAuthorize("hasAuthority('stock:query')")
    @ApiOperation("获取所有阈值之下的物资数据")
    @GetMapping("/findAllBelowThreshold")
    public Result findAllBelowThreshold(
            @ApiParam(name = "r_id", value = "餐馆ID", example = "1238843327436247123")
            @RequestParam String r_id) {
        Restaurant restaurant = restaurantService.findById(r_id);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        return Result.ok().data("rows", stockService.findAllBelowThreshold(r_id));
    }


    /*
     *
     * 第四次迭代接口
     *
     */

    @ApiOperation("分页查询库存，可指定排序方式等，比findPage多了阈值等功能")
    @PreAuthorize("hasAuthority('stock:query')")
    @GetMapping("/findBelowThreshold")
    public Result findBelowThreshold(
            @ApiParam(name = "pageId", value = "页码", example = "1")
            @RequestParam(required = true) Integer pageId,
            @ApiParam(name = "pageSize", value = "每页条数", example = "5")
            @RequestParam(required = true) Integer pageSize,
            @ApiParam(name = "r_id", value = "餐馆ID", example = "1238843327436247123")
            @RequestParam(required = true) String r_id,
            @ApiParam(name = "cateId", value = "分类ID", example = "0")
            @RequestParam(required = false) String cateId,
            @ApiParam(name = "sortColumn", value = "排序字段：s_stock, modify_time, abundant", example = "s_stock")
            @RequestParam(required = false) String sortColumn,
            @ApiParam(name = "sortOrder", value = "排序规则", example = "asc")
            @RequestParam(required = false) String sortOrder,
            @ApiParam(name = "belowThreshold", value = "是否在阈值之下:0, 1", example = "0")
            @RequestParam(required = false) Integer belowThreshold,
            @ApiParam(name = "keyword", value = "关键词", example = "XXX")
            @RequestParam(required = false) String keyword) {
        Restaurant restaurant = restaurantService.findById(r_id);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");

        List<String> columnNames = Arrays.asList("s_stock", "modify_time", "abundant");
        if (!columnNames.contains(sortColumn) && sortColumn != null)
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder) && sortOrder != null)
            return Result.error().message("sortOrder参数必须是asc或desc");
        return stockService.findBelowThreshold(pageId, pageSize, r_id, cateId, sortColumn, sortOrder, belowThreshold, keyword);
    }



    /*
     *
     * 第三次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('stock:query')")
    @ApiOperation("获取一个餐厅的所有库存(名字，剩余)信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "餐厅ID", dataTypeClass = String.class),
    })
    @GetMapping("/getAllInRestaurant/{restaurantId}")
    public Result getAllInRestaurant(@PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");

        return stockService.getInRestaurant(restaurantId);
    }

    /*
     *
     * 第一次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('stock:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageCount", value = "每页条数", dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{current}/{limit}")
    public Result findListByPage(@PathVariable Integer current,
                                 @PathVariable Integer limit) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        Page<Stock> stockPage = new Page<>(current, limit);
        stockService.page(stockPage, null);
        long total = stockPage.getTotal();   //总记录数
        List<Stock> records = stockPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('stock:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody Stock stock) {
        if (!securityService.isAccessible(stock))
            return Result.error().message("无权访问");
        stockService.add(stock);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('stock:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        Stock stock = stockService.findById(id);
        if (stock == null)
            return Result.error().message("指定的库存不存在");
        if (!securityService.isAccessible(stock))
            return Result.error().message("无权访问");
        stockService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('stock:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody Stock stock) {
        Stock stock1 = stockService.findById(stock.getSId());
        if (stock1 == null)
            return Result.error().message("指定的库存不存在");
        if (!securityService.isAccessible(stock1))
            return Result.error().message("无权访问");
        stockService.updateData(stock);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('stock:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Stock stock = stockService.findById(id);
        if (stock == null)
            return Result.error().message("指定的库存不存在");
        if (!securityService.isAccessible(stock))
            return Result.error().message("无权访问");
        return Result.ok().data("stock", stock);
    }

}
