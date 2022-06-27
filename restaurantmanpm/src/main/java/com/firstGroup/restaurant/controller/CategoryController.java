package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Category;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.vo.CategoryVo;
import com.firstGroup.restaurant.model.vo.CategoryVo1;
import com.firstGroup.restaurant.model.vo.DishListVo;
import com.firstGroup.restaurant.service.ICategoryService;
import com.firstGroup.restaurant.service.impl.RecommendDishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = {"分类数据"})
@RestController
@RequestMapping("/category")
public class CategoryController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private RecommendDishService recommendDishService;


    @ApiOperation(value = "获取推荐商品")
    @GetMapping("/getRecommendDishes/{cId}/{number}")
    public Result getRecommendDishes(
            @ApiParam(value = "用户ID", required = true, example = "2")
            @PathVariable String cId,
            @ApiParam(value = "推荐数量", required = true, example = "2")
            @PathVariable Integer number) {
        // 推荐商品
        List<Dish> dishes = null;
        try {
            dishes = recommendDishService.userCFRecommend(cId, number,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok().data("dishes",dishes);
    }



    /*
     *
     * 第三次迭代接口
     *
     */

    @ApiOperation("分页查询分类，可指定排序方式、模糊查询关键字")
    @GetMapping("/findPage")
    public Result findPage(
            @ApiParam(value = "页码", required = true, example = "0")
            @RequestParam Integer pageId,
            @ApiParam(value = "每页记录数", required = true, example = "5")
            @RequestParam Integer pageSize,
            @ApiParam(value = "0表示查询菜品分类，1表示查询配料分类",
                    required = true, example = "0")
            @RequestParam Integer categoryType,
            @ApiParam(value = "排序字段，可选值：cate_id、cate_name、create_time",
                    required = true, example = "cate_id")
            @RequestParam String sortColumn,
            @ApiParam(value = "升序还是降序，可选值：asc、desc", required = true, example = "asc")
            @RequestParam String sortOrder,
            @ApiParam(value = "模糊查询关键字，非必需", example = "XXX")
            @RequestParam(required = false) String keyword) {
        List<String> columnNames = Arrays.asList(
                "cate_id", "cate_name", "create_time");
        if (!columnNames.contains(sortColumn))
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder))
            return Result.error().message("sortOrder参数必须是asc或desc");
        return Result.ok().data("page", categoryService.findPage(
                pageId, pageSize, categoryType, sortColumn, sortOrder, keyword));
    }

    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Category category = categoryService.findById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return Result.ok().data("category", categoryVo);
    }

    @PreAuthorize("hasAuthority('category:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        int i = categoryService.delete(id);
        if(i==0){
            return Result.error().message("删除失败！");
        }
        return Result.ok().message("删除成功！");
    }

    @PreAuthorize("hasAuthority('category:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody CategoryVo1 categoryVo1) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryVo1,category);
        int i = categoryService.updateData(category);
        if(i==0){
            return Result.error().message("更新失败！");
        }
        return Result.ok().message("更新成功！");
    }

    @PreAuthorize("hasAuthority('category:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody CategoryVo categoryVo) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryVo,category);
        int i = categoryService.add(category);
        if(i==0){
            return Result.error().message("添加失败！");
        }
        return Result.ok().message("添加成功！");
    }


    /*
     *
     * 第二次迭代接口
     *
     */
    @ApiOperation(value = "首页获取所有分类和菜品，包括优惠与热销与推荐")
    @GetMapping("/getAllInfo/{cId}")
    public Result getAllInfo(
            @ApiParam(value = "用户ID", required = true, example = "2")
            @PathVariable String cId) {
        List<DishListVo> dishList = categoryService.getAllInfo(cId);
        return Result.ok().data("dishList", dishList);
    }

    @ApiOperation("获取所有菜品分类")
    @GetMapping("/findAllForDish")
    public Result findAllForDish() {
        List<Category> categories = categoryService.findAllForDish();
        return Result.ok().data("categories", categories);
    }

}
