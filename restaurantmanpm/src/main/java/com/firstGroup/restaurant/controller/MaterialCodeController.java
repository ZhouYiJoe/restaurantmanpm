package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.MaterialCode;
import com.firstGroup.restaurant.model.vo.MaterialCodeVo;
import com.firstGroup.restaurant.service.IMaterialCodeService;
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
@Api(tags = {"材料数据"})
@RestController
@RequestMapping("/material-code")
public class MaterialCodeController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMaterialCodeService materialCodeService;

    /*
     *
     * 第五次迭代接口
     *
     */
    @PreAuthorize("hasAuthority('material_code:query')")
    @ApiOperation("获取所有的配料名字和单位")
    @GetMapping("/findAllNameAndUnit")
    public Result findAllNameAndUnit() {
        List<MaterialCode> list = materialCodeService.findAllNameAndUnit();
        return Result.ok().data("mcList",list);
    }


    /*
     *
     * 第四次迭代接口
     *
     */
    @PreAuthorize("hasAuthority('material_code:query')")
    @ApiOperation("分页查询物资，可指定分类、排序方式、模糊查询关键字")
    @GetMapping("/findPage")
    public Result findPage(
            @ApiParam(value = "页码", required = true, example = "0")
            @RequestParam Integer pageId,
            @ApiParam(value = "每页记录数", required = true, example = "5")
            @RequestParam Integer pageSize,
            @ApiParam(value = "分类ID", example = "0")
            @RequestParam(required = false) String categoryId,
            @ApiParam(value = "排序字段，可选值：mc_priceperunit、create_time", example = "mc_priceperunit")
            @RequestParam(required = false) String sortColumn,
            @ApiParam(value = "升序还是降序，可选值：asc、desc", example = "asc")
            @RequestParam(required = false) String sortOrder,
            @ApiParam(value = "模糊查询关键字，非必需", example = "XXX")
            @RequestParam(required = false) String keyword) {
        List<String> columnNames = Arrays.asList(
                "mc_priceperunit", "create_time");
        if (!columnNames.contains(sortColumn) && sortColumn != null)
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder) && sortOrder != null)
            return Result.error().message("sortOrder参数必须是asc或desc");
        return Result.ok().data("page", materialCodeService.findPage(pageId, pageSize, categoryId, sortColumn, sortOrder, keyword));
    }

    @PreAuthorize("hasAuthority('material_code:query')")
    @ApiOperation("获取所有的配料")
    @GetMapping("/findAll")
    public Result findAll() {
        List<MaterialCodeVo> list = materialCodeService.findAll();
        return Result.ok().data("mcList",list);
    }

    @PreAuthorize("hasAuthority('material_code:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageCount", value = "每页条数",dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{current}/{limit}")
    public Result findListByPage(@PathVariable Integer current,
                                 @PathVariable Integer limit) {
        Page<MaterialCode> materialCodePage = new Page<>(current, limit);
        materialCodeService.page(materialCodePage, null);
        long total = materialCodePage.getTotal();   //总记录数
        List<MaterialCode> records = materialCodePage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('material_code:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody MaterialCode materialCode) {
        materialCodeService.add(materialCode);
        return Result.ok().data("materialCode", materialCode);
    }

    @PreAuthorize("hasAuthority('material_code:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        materialCodeService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('material_code:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody MaterialCode materialCode) {
        materialCodeService.updateData(materialCode);
        return Result.ok();
    }

    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        return Result.ok().data("materialCode", materialCodeService.findById(id));
    }

}
