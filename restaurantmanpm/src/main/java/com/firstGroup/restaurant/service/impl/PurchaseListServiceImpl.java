package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.PurchaseListMapper;
import com.firstGroup.restaurant.model.*;
import com.firstGroup.restaurant.model.vo.PurchaseListVos;
import com.firstGroup.restaurant.service.IPurchaseListService;
import com.firstGroup.restaurant.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class PurchaseListServiceImpl extends ServiceImpl<PurchaseListMapper, PurchaseList> implements IPurchaseListService {

    @Autowired
    private BuyerServiceImpl buyerService;

    @Autowired
    private MaterialCodeServiceImpl materialCodeService;

    @Autowired
    private ISecurityService securityService;

    @Override
    public int add(PurchaseList purchaseList) {
        return baseMapper.insert(purchaseList);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(PurchaseList purchaseList) {
        return baseMapper.updateById(purchaseList);
    }

    @Override
    public PurchaseList findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Result findByPurchaseRecordId(String purchaseRecordId) {

        QueryWrapper<PurchaseList> listWrapper = new QueryWrapper<>();
        listWrapper.eq("pr_id", purchaseRecordId);
        List<PurchaseList> purchaseLists = baseMapper.selectList(listWrapper);
        List<Map<String, Object>> res = new ArrayList<>();
        for (PurchaseList purchaseList : purchaseLists) {

            Map<String, Object> map = new HashMap<>();
            // 获取进货商姓名
            QueryWrapper<Buyer> byWrapper = new QueryWrapper<>();
            byWrapper.select("b_name")
                    .eq("b_id", purchaseList.getBId());
            Buyer buyer = buyerService.getBaseMapper().selectOne(byWrapper);
            if (buyer != null) {
                map.put("buyerName", buyer.getBName());
            }

            // 获取材料信息
            QueryWrapper<MaterialCode> mcWrapper = new QueryWrapper<>();
            mcWrapper.eq("mc_id", purchaseList.getMcId());

            MaterialCode mc = materialCodeService.getBaseMapper().selectOne(mcWrapper);
            if (mc != null) {
                map.put("mcName", mc.getMcName());
                map.put("mcDescription", mc.getMcDescription());
                map.put("mcImage_url", mc.getMcImgurl());
                map.put("mcExpectedNumber", purchaseList.getExpectedNumber());
                map.put("mcExpectedPrice", purchaseList.getExpectedPrice());
                map.put("mcActualNumber", purchaseList.getActualNumber());
                map.put("mcActualPrice", purchaseList.getActualPrice());
            }
            if (buyer != null || mc != null) {
                map.put("purchaseListId", purchaseList.getId());
                map.put("modifyTime", purchaseList.getModifyTime());
                res.add(map);
            }
        }
        return Result.ok().data("rows", res).data("total", purchaseLists.size());
    }



    @Override
    public Result findPage(Integer pageId,
                           Integer pageSize,
                           String sortColumn,
                           String sortOrder,
                           String keyword) {
        QueryWrapper<PurchaseList> wrapper = new QueryWrapper<>();
        if (sortColumn != null) {
            if (sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    wrapper.orderByAsc(sortColumn);
                } else if (sortOrder.equals("desc")) {
                    wrapper.orderByDesc(sortColumn);
                }
            } else {
                wrapper.orderByDesc(sortColumn);
            }
        }

        IPage<PurchaseList> page = baseMapper.selectPage(new Page<>(pageId, pageSize), wrapper);
        List<PurchaseList> purchaseLists = page.getRecords();
        List<Map<String, Object>> list = new ArrayList<>();
        for (PurchaseList purchaseList : purchaseLists) {
            Map<String, Object> map = new HashMap<>();

            // 获取进货商姓名
            QueryWrapper<Buyer> byWrapper = new QueryWrapper<>();
            byWrapper.select("b_name")
                    .eq("b_id", purchaseList.getBId());
            Buyer buyer = buyerService.getBaseMapper().selectOne(byWrapper);
            if (buyer != null) {
                map.put("buyerName", buyer.getBName());
            }

            // 获取材料信息
            QueryWrapper<MaterialCode> mcWrapper = new QueryWrapper<>();
            mcWrapper.eq("mc_id", purchaseList.getMcId());

            MaterialCode mc = materialCodeService.getBaseMapper().selectOne(mcWrapper);
            if (mc != null) {
                map.put("mcName", mc.getMcName());
                map.put("mcDescription", mc.getMcDescription());
                map.put("mcImage_url", mc.getMcImgurl());
                map.put("mcExpectedNumber", purchaseList.getExpectedNumber());
                map.put("mcExpectedPrice", purchaseList.getExpectedPrice());
            }
            if (buyer != null || mc != null) {
                map.put("purchaseListId", purchaseList.getId());
                map.put("modifyTime", purchaseList.getModifyTime());
                list.add(map);
            }
        }
        return Result.ok().data("rows", list).data("total", page.getTotal());
    }

    @Override
    public Result updatePriceAndNumber(List<PurchaseListVos> purchaseListVos) {
        for (PurchaseListVos purchaseListVo : purchaseListVos) {
            UpdateWrapper<PurchaseList> purchaseListUpdateWrapper = new UpdateWrapper<>();
            String purchaseListId = purchaseListVo.getPurchaseListId();
            BigDecimal price = purchaseListVo.getMcActualPrice();
            BigDecimal number = purchaseListVo.getMcActualNumber();
            PurchaseList purchaseList = findById(purchaseListId);
            if (purchaseList == null)
                return Result.error().message("指定的采购清单不存在");
            if (!securityService.isAccessible(purchaseList))
                return Result.error().message("无权访问");
            purchaseListUpdateWrapper.eq("id", purchaseListId);
            if (price != null)
                purchaseListUpdateWrapper.set("actual_price", price);
            if (number != null)
                purchaseListUpdateWrapper.set("actual_number", number);
            baseMapper.update(null, purchaseListUpdateWrapper);
        }
        return Result.ok();
    }
}
