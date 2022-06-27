package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.PurchaseList;
import com.firstGroup.restaurant.model.PurchaseRecord;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.PurchaseListVos;
import com.firstGroup.restaurant.service.IPurchaseListService;
import com.firstGroup.restaurant.service.IPurchaseRecordService;
import com.firstGroup.restaurant.service.ISecurityService;
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
@Api(tags = {"采购清单数据"})
@RestController
@RequestMapping("/purchase-list")
public class PurchaseListController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPurchaseListService purchaseListService;
    @Autowired
    private ISecurityService securityService;
    @Autowired
    private IPurchaseRecordService purchaseRecordService;

    /*
     *
     * 第六次迭代接口
     *
     */
    @PreAuthorize("hasAuthority('purchase_list:update')")
    @ApiOperation(value = "修改指定采购清单的价格和数量")
    @PostMapping("/updatePriceAndNumber")
    public Result updatePriceAndNumber(@RequestBody List<PurchaseListVos> purchaseListVos) {
        return purchaseListService.updatePriceAndNumber(purchaseListVos);
    }

    @PreAuthorize("hasAuthority('purchase_list:query')")
    @ApiOperation(value = "查询指定采购记录下的采购清单")
    @GetMapping("/findByPurchaseRecordId")
    public Result findByPurchaseRecordId(
            @ApiParam(name = "purchaseRecordId", value = "采购记录id", example = "1")
            @RequestParam String purchaseRecordId) {
        PurchaseRecord purchaseRecord = purchaseRecordService.findById(purchaseRecordId);
        if (purchaseRecord == null)
            return Result.error().message("指定的采购清单不存在");
        if (!securityService.isAccessible(purchaseRecord))
            return Result.error().message("无权访问");
        return purchaseListService.findByPurchaseRecordId(purchaseRecordId);
    }

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('purchase_list:query')")
    @ApiOperation("分页查询采购清单，可指定排序方式等")
    @GetMapping("/findPage")
    public Result findPage(
            @ApiParam(name = "pageId", value = "页码", example = "1")
            @RequestParam Integer pageId,
            @ApiParam(name = "pageSize", value = "每页条数", example = "5")
            @RequestParam Integer pageSize,
            @ApiParam(name = "sortColumn", value = "排序字段 actual_price, modify_time", example = "modify_time")
            @RequestParam(required = false) String sortColumn,
            @ApiParam(name = "sortOrder", value = "排序规则", example = "asc")
            @RequestParam(required = false) String sortOrder,
            @ApiParam(name = "keyword", value = "关键词，可以按照进货商或材料名查询，只可选其一", example = "XXX")
            @RequestParam(required = false) String keyword) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        List<String> columnNames = Arrays.asList("actual_price", "modify_time");
        if (!columnNames.contains(sortColumn) && sortColumn != null)
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder) && sortOrder != null)
            return Result.error().message("sortOrder参数必须是asc或desc");
        return purchaseListService.findPage(pageId, pageSize, sortColumn, sortOrder, keyword);
    }


    /*
     *
     * 第一次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('purchase_list:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageCount", value = "每页条数", dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{current}/{limit}")
    public Result findListByPage(@PathVariable Integer current,
                                 @PathVariable Integer limit) {
        Page<PurchaseList> purchaseListPage = new Page<>(current, limit);
        purchaseListService.page(purchaseListPage, null);
        long total = purchaseListPage.getTotal();   //总记录数
        List<PurchaseList> records = purchaseListPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('purchase_list:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody PurchaseList purchaseList) {
        if (!securityService.isAccessible(purchaseList))
            return Result.error().message("无权访问");
        purchaseListService.add(purchaseList);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('purchase_list:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        PurchaseList purchaseList = purchaseListService.findById(id);
        if (purchaseList == null)
            return Result.error().message("指定的采购清单不存在");
        if (!securityService.isAccessible(purchaseList))
            return Result.error().message("无权访问");
        purchaseListService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('purchase_list:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody PurchaseList purchaseList) {
        PurchaseList purchaseList1 = purchaseListService.findById(purchaseList.getId());
        if (purchaseList1 == null)
            return Result.error().message("指定的采购清单不存在");
        if (!securityService.isAccessible(purchaseList1))
            return Result.error().message("无权访问");
        purchaseListService.updateData(purchaseList);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('purchase_list:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        PurchaseList purchaseList = purchaseListService.findById(id);
        if (purchaseList == null)
            return Result.error().message("指定的采购清单不存在");
        if (!securityService.isAccessible(purchaseList))
            return Result.error().message("无权访问");
        return Result.ok().data("purchaseList", purchaseList);
    }

}
