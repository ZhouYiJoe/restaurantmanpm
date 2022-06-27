package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.PurchaseRecord;
import com.firstGroup.restaurant.model.vo.PurchaseRecordVo;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-21
 */
public interface IPurchaseRecordService extends IService<PurchaseRecord> {

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<PurchaseRecord>
     */
    IPage<PurchaseRecord> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param purchaseRecord 
     * @return int
     */
    int add(PurchaseRecord purchaseRecord);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(String id);

    /**
     * 修改
     *
     * @param purchaseRecord 
     * @return int
     */
    int updateData(PurchaseRecord purchaseRecord);

    /**
     * id查询数据
     *
     * @param id id
     * @return PurchaseRecord
     */
    PurchaseRecord findById(String id);

    /**
     * 采购清单页，可以选择排序方式，也可以筛选关键词
     * @param pageId
     * @param pageSize
     * @param r_id
     * @param sortColumn
     * @param sortOrder
     * @param keyword
     * @param from         如果为null，则不限定日期范围，查询所有数据
     * @param to           如果为null，则不限定日期范围，查询所有数据
     * @return
     */
    Result findPage(Integer pageId, Integer pageSize, String r_id, String sortColumn, String sortOrder, String keyword, Date from, Date to);

    /**
     * 修改指定采购记录状态
     *
     * @param purchaseRecordId purchaseRecordId
     * @return Result
     */
    Result updateStatusByPurchaseRecordId(String purchaseRecordId, String aId, String note);

    /**
     * 创建采购记录
     *
     * @param purchaseRecordVo
     * @return String
     */
    String createPurchaseRecord(PurchaseRecordVo purchaseRecordVo);

}
