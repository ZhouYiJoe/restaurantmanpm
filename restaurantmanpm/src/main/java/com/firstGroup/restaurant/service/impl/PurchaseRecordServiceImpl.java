package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.PurchaseRecordMapper;
import com.firstGroup.restaurant.model.PurchaseList;
import com.firstGroup.restaurant.model.PurchaseRecord;
import com.firstGroup.restaurant.model.vo.PurchaseListVo;
import com.firstGroup.restaurant.model.vo.PurchaseRecordPageVo;
import com.firstGroup.restaurant.model.vo.PurchaseRecordVo;
import com.firstGroup.restaurant.service.IPurchaseRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-21
 */
@Service
public class PurchaseRecordServiceImpl extends ServiceImpl<PurchaseRecordMapper, PurchaseRecord> implements IPurchaseRecordService {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private PurchaseListServiceImpl purchaseListService;


    @Override
    public IPage<PurchaseRecord> findListByPage(Integer page, Integer pageCount){
        IPage<PurchaseRecord> wherePage = new Page<>(page, pageCount);
        PurchaseRecord where = new PurchaseRecord();
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(PurchaseRecord purchaseRecord){
        return baseMapper.insert(purchaseRecord);
    }

    @Override
    public int delete(String id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(PurchaseRecord purchaseRecord){
        return baseMapper.updateById(purchaseRecord);
    }

    @Override
    public PurchaseRecord findById(String id){
        return baseMapper.selectById(id);
    }

    @Override
    public Result findPage(Integer pageId,
                           Integer pageSize,
                           String r_id,
                           String sortColumn,
                           String sortOrder,
                           String keyword,
                           Date from,
                           Date to) {
        List<PurchaseRecordPageVo> list = baseMapper.findPage((pageId - 1) * pageSize, pageSize, r_id, sortColumn, sortOrder, keyword, from, to);
        Integer total = baseMapper.findRestaurantPageTotal(r_id, keyword, from, to);
        return Result.ok().data("rows", list).data("total", total);
    }

    @Override
    public Result updateStatusByPurchaseRecordId(String purchaseRecordId, String aId, String note) {
        UpdateWrapper<PurchaseRecord> purchaseRecordUpdateWrapper = new UpdateWrapper<>();
        purchaseRecordUpdateWrapper.eq("id", purchaseRecordId).set("status", 1);
        if (note != null)
            purchaseRecordUpdateWrapper.set("note", note);
        if (aId != null)
            purchaseRecordUpdateWrapper.set("a_id", aId);
        baseMapper.update(null, purchaseRecordUpdateWrapper);
        return Result.ok();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)//修改隔离和传播机制
    public String createPurchaseRecord(PurchaseRecordVo purchaseRecordVo) {

        PurchaseRecord purchaseRecord = new PurchaseRecord();
        purchaseRecord.setAId(purchaseRecordVo.getAId());
        purchaseRecord.setRId(purchaseRecordVo.getRId());
        baseMapper.insert(purchaseRecord);
        BigDecimal expectedTotalPrice = new BigDecimal(0);
        String purchaseRecordId = purchaseRecord.getId();
        List<PurchaseListVo> purchaseListVos = purchaseRecordVo.getPurchaseListVos();
        for (PurchaseListVo purchaseListVo: purchaseListVos) {
            PurchaseList purchaseList = new PurchaseList();
            BeanUtils.copyProperties(purchaseListVo, purchaseList);
            purchaseList.setPrId(purchaseRecordId);
            expectedTotalPrice = expectedTotalPrice.add(purchaseList.getExpectedPrice());
            purchaseListService.add(purchaseList);
        }
        UpdateWrapper<PurchaseRecord> purchaseRecordUpdateWrapper = new UpdateWrapper<>();
        purchaseRecordUpdateWrapper.eq("id", purchaseRecordId).set("expected_total_price", expectedTotalPrice);
        baseMapper.update(null, purchaseRecordUpdateWrapper);
        return purchaseRecordId;
    }
}
