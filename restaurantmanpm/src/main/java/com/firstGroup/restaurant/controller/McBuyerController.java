package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.McBuyer;
import com.firstGroup.restaurant.service.IMcBuyerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-24
 */
@Api(tags = "材料-进货商关系")
@RestController
@RequestMapping("/mc-buyer")
public class McBuyerController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMcBuyerService mcBuyerService;


    // 第五次迭代

    @PreAuthorize("hasAuthority('mc_buyer:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public int add(@RequestBody McBuyer mcBuyer){
        return mcBuyerService.add(mcBuyer);
    }

    @PreAuthorize("hasAuthority('mc_buyer:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public int delete(@PathVariable("id") Long id){
        return mcBuyerService.delete(id);
    }

    @PreAuthorize("hasAuthority('mc_buyer:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public int update(@RequestBody McBuyer mcBuyer){
        return mcBuyerService.updateData(mcBuyer);
    }

    @PreAuthorize("hasAuthority('mc_buyer:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页码", dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "pageCount", value = "每页条数", dataTypeClass = Integer.class)
    })
    @GetMapping()
    public Result findListByPage(@RequestParam Integer page,
                                 @RequestParam Integer pageCount){
        return Result.ok().data("pages", mcBuyerService.findListByPage(page, pageCount));
    }

    @PreAuthorize("hasAuthority('mc_buyer:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public McBuyer findById(@PathVariable Long id){
        return mcBuyerService.findById(id);
    }

}
