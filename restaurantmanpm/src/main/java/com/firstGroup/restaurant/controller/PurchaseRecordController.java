package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.PurchaseRecord;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.PurchaseRecordVo;
import com.firstGroup.restaurant.service.IPurchaseRecordService;
import com.firstGroup.restaurant.service.IRestaurantService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-21
 */
@Api(tags = {"采购记录数据"})
@RestController
@RequestMapping("/purchase-record")
public class PurchaseRecordController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPurchaseRecordService purchaseRecordService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISecurityService securityService;



    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('purchase_record:query')")
    @ApiOperation("分页查询采购记录，可指定排序方式等")
    @GetMapping("/findPage")
    public Result findPage(
            @ApiParam(name = "pageId", value = "页码", example = "1")
            @RequestParam(required = true) Integer pageId,
            @ApiParam(name = "pageSize", value = "每页条数", example = "5")
            @RequestParam(required = true) Integer pageSize,
            @ApiParam(name = "r_id", value = "餐馆ID", example = "1238843327436247123")
            @RequestParam(required = false) String r_id,
            @ApiParam(name = "sortColumn", value = "排序字段 status, expected_total_price, actual_total_price, modify_time", example = "modify_time")
            @RequestParam(required = false) String sortColumn,
            @ApiParam(name = "sortOrder", value = "排序规则", example = "asc")
            @RequestParam(required = false) String sortOrder,
            @ApiParam(name = "keyword", value = "关键词，可以按照处理人姓名查询", example = "XXX")
            @RequestParam(required = false) String keyword,
            @ApiParam(value = "起始日期，非必需", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = false) Date from,
            @ApiParam(value = "终止日期，非必需", example = "XXX")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = false) Date to) {
        Restaurant restaurant = restaurantService.findById(r_id);
        if (restaurant == null)
            return Result.error().message("指定的餐厅不存在");
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        List<String> columnNames = Arrays.asList("status", "expected_total_price", "actual_total_price", "modify_time");
        if (!columnNames.contains(sortColumn) && sortColumn != null)
            return Result.error().message("无效的排序字段名");
        if (!"asc".equals(sortOrder) && !"desc".equals(sortOrder) && sortOrder != null)
            return Result.error().message("sortOrder参数必须是asc或desc");
        return purchaseRecordService.findPage(pageId, pageSize, r_id, sortColumn, sortOrder, keyword, from, to);
    }

    @PreAuthorize("hasAuthority('purchase_record:update')")
    @ApiOperation(value = "完成指定采购记录")
    @PostMapping("/updateStatusByPurchaseRecordId")
    public Result updateStatusByPurchaseRecordId(
            @ApiParam(name = "purchaseRecordId", value = "采购记录id", example = "1")
            @RequestParam String purchaseRecordId,
            @ApiParam(name = "aId", value = "处理人（管理员）id", example = "1")
            @RequestParam(required = false) String aId,
            @ApiParam(name = "note", value = "采购记录备注", example = "1")
            @RequestParam(required = false) String note) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        String adminId = userDetails.getUsername();
        if (!adminId.equals(aId))
            return Result.error().message("无权访问");
        PurchaseRecord purchaseRecord = purchaseRecordService.findById(purchaseRecordId);
        if (purchaseRecord == null)
            return Result.error().message("指定的采购记录不存在");
        if (!securityService.isAccessible(purchaseRecord))
            return Result.error().message("无权访问");
        return purchaseRecordService.updateStatusByPurchaseRecordId(purchaseRecordId, aId, note);
    }

    @PreAuthorize("hasAuthority('purchase_record:add')")
    @ApiOperation(value = "创建采购记录")
    @PostMapping("/createPurchaseRecord")
    public Result createPurchaseRecord(@RequestBody PurchaseRecordVo purchaseRecordVo) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (!userDetails.getUsername().equals(purchaseRecordVo.getAId()))
            return Result.error().message("无权访问");
        Restaurant restaurant = restaurantService.findById(purchaseRecordVo.getRId());
        if (!securityService.isAccessible(restaurant))
            return Result.error().message("无权访问");
        String purchaseRecordId = purchaseRecordService.createPurchaseRecord(purchaseRecordVo);
        return Result.ok().data("purchaseRecordId", purchaseRecordId);
    }

    @PreAuthorize("hasAuthority('purchase_record:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        PurchaseRecord purchaseRecord = purchaseRecordService.findById(id);
        if (purchaseRecord == null)
            return Result.error().message("指定的采购记录不存在");
        if (!securityService.isAccessible(purchaseRecord))
            return Result.error().message("无权访问");
        purchaseRecordService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('purchase_record:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody PurchaseRecord PurchaseRecord) {
        PurchaseRecord purchaseRecord = purchaseRecordService.findById(PurchaseRecord.getId());
        if (purchaseRecord == null)
            return Result.error().message("指定的采购记录不存在");
        if (!securityService.isAccessible(purchaseRecord))
            return Result.error().message("无权访问");
        purchaseRecordService.updateData(PurchaseRecord);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('purchase_record:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageCount", value = "每页条数",dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{current}/{limit}")
    public Result findListByPage(@PathVariable Integer current,
                            @PathVariable Integer limit){
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        Page<PurchaseRecord> purchaseRecordPage = new Page<>(current, limit);
        purchaseRecordService.page(purchaseRecordPage, null);
        long total = purchaseRecordPage.getTotal();   //总记录数
        List<PurchaseRecord> records = purchaseRecordPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('purchase_record:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id){
        PurchaseRecord purchaseRecord = purchaseRecordService.findById(id);
        if (purchaseRecord == null)
            return Result.error().message("指定的采购记录不存在");
        if (!securityService.isAccessible(purchaseRecord))
            return Result.error().message("无权访问");
        return Result.ok().data("purchaseRecord", purchaseRecord);
    }

}
