package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Buyer;
import com.firstGroup.restaurant.service.IBuyerService;
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
@Api(tags = {"进货商数据"})
@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private Logger log = LoggerFactory.getLogger(getClass());


    /*
     *
     * 第一次迭代接口
     *
     */

    @Autowired
    private IBuyerService buyerService;

    @PreAuthorize("hasAuthority('buyer:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageCount", value = "每页条数",dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{current}/{limit}")
    public Result findListByPage(@PathVariable Integer current,
                                 @PathVariable Integer limit) {
        Page<Buyer> buyerPage = new Page<>(current, limit);
        buyerService.page(buyerPage, null);
        long total = buyerPage.getTotal();   //总记录数
        List<Buyer> records = buyerPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('buyer:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody Buyer buyer) {
        buyerService.add(buyer);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('buyer:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        buyerService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('buyer:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody Buyer buyer) {
        buyerService.updateData(buyer);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('buyer:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        Buyer buyer = buyerService.findById(id);
        return Result.ok().data("buyer",buyer);
    }

}
