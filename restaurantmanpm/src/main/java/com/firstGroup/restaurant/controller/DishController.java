package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.vo.DishVo;
import com.firstGroup.restaurant.model.vo.GetDishVo;
import com.firstGroup.restaurant.model.vo.DishSalesIncrement;
import com.firstGroup.restaurant.service.IDishService;
import com.firstGroup.restaurant.service.ISecurityService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
@Api(tags = {"菜品数据"})
@RestController
@RequestMapping("/dish")
public class DishController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDishService dishService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第五次迭代接口
     *
     */

    @ApiOperation("增加销量")
    @PostMapping("/increaseSales/{dishId}")
    public Result increaseSales(@PathVariable String dishId,
                                @RequestBody DishSalesIncrement increment) {
        dishService.increaseSales(dishId, increment.getIncrement());
        return Result.ok();
    }

    /*
     *
     * 第四次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('dish:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody DishVo dishVo) {
        Dish dish = new Dish();
        dish.setRId(dishVo.getRId());
        if (!securityService.isAccessible(dish))
            return Result.error().message("无权访问");
        dishService.add(dishVo);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('dish:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody GetDishVo dishVo) {
        Dish dish = dishService.getById(dishVo.getDId());
        if (!securityService.isAccessible(dish))
            return Result.error().message("无权访问");
        GetDishVo getDishVo = dishService.findById(dishVo.getDId());
        BeanUtils.copyProperties(dishVo, getDishVo);
        dishService.updateData(getDishVo);
        return Result.ok();
    }

    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        GetDishVo getDishVo = dishService.findById(id);
        return Result.ok().data("dish", getDishVo);
    }


    /*
     * 第三次迭代接口
     */

    @ApiOperation("获取一个餐厅销量排行前N的菜品")
    @GetMapping("/getSalesTopN/{restaurantId}/{n}")
    public Result getSalesTopN(
            @ApiParam(value = "餐厅ID", required = true, example = "0")
            @PathVariable String restaurantId,
            @ApiParam(value = "指定获取销量排行前几的菜品", required = true, example = "5")
            @PathVariable Integer n) {
        if (n <= 0)
            return Result.error().message("参数n必须为正整数");
        return Result.ok().data("topN", dishService.getSalesTopN(restaurantId, n));
    }

    @ApiOperation("分页查询菜品，可指定餐厅、菜品分类、排序方式、模糊查询关键字")
    @GetMapping("/findPage")
    public Result findPage(
            @ApiParam(value = "页码", required = true, example = "0")
            @RequestParam Integer pageId,
            @ApiParam(value = "每页记录数", required = true, example = "5")
            @RequestParam Integer pageSize,
            @ApiParam(value = "餐厅ID", required = true, example = "0")
            @RequestParam String restaurantId,
            @ApiParam(value = "菜品分类ID", required = true, example = "0")
            @RequestParam String categoryId,
            @ApiParam(value = "排序字段，可选值：d_id、d_name、d_price、create_time、d_sales",
                    required = true, example = "d_id")
            @RequestParam String sortColumn,
            @ApiParam(value = "升序还是降序，可选值：asc、desc", required = true, example = "asc")
            @RequestParam String sortOrder,
            @ApiParam(value = "模糊查询关键字，非必需", example = "XXX")
            @RequestParam(required = false) String keyword) {
        List<String> columnNames = Arrays.asList(
                "d_id", "d_name", "d_price", "create_time", "d_sales");
        if (!columnNames.contains(sortColumn))
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder))
            return Result.error().message("sortOrder参数必须是asc或desc");
        return Result.ok().data("page", dishService.findPage(
                pageId, pageSize, restaurantId, categoryId, sortColumn, sortOrder, keyword));
    }

    @PreAuthorize("hasAuthority('dish:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        Dish dish = dishService.getById(id);
        if (dish == null)
            return Result.error().message("指定的菜品不存在");
        if (!securityService.isAccessible(dish))
            return Result.error().message("无权访问");
        dishService.delete(id);
        return Result.ok();
    }

    /*
     *
     * 第一次迭代接口
     *
     */

    @ApiOperation("分页显示某种分类下的菜品")
    @GetMapping("/findByCategory/{category_id}/{current}/{limit}")
    public Result findByCategory(
            @ApiParam(value = "菜品分类编号", example = "0000000000000000008")
            @PathVariable String category_id,
            @ApiParam(value = "页码", example = "1")
            @PathVariable Integer current,
            @ApiParam(value = "每页条数", example = "5")
            @PathVariable Integer limit) {

        Page<Dish> dishPage = new Page<>(current, limit);
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        wrapper.eq("cate_id", category_id);
        dishService.page(dishPage, wrapper);
        long total = dishPage.getTotal();   //总记录数

        List<Dish> records = dishPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limit", value = "每页条数",dataTypeClass = Integer.class)
    })
    @GetMapping()
    public Result findListByPage(@RequestParam Integer current,
                                 @RequestParam Integer limit) {
        Page<Dish> dishPage = new Page<>(current, limit);
        dishService.page(dishPage, null);
        long total = dishPage.getTotal();   //总记录数
        List<Dish> records = dishPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

}
