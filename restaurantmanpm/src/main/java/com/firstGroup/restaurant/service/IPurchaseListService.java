package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.PurchaseList;
import com.firstGroup.restaurant.model.vo.PurchaseListVos;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IPurchaseListService extends IService<PurchaseList> {

    /**
     * 添加
     *
     * @param purchaseList 
     * @return int
     */
    int add(PurchaseList purchaseList);

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
     * @param purchaseList 
     * @return int
     */
    int updateData(PurchaseList purchaseList);

    /**
     * id查询数据
     *
     * @param id id
     * @return PurchaseList
     */
    PurchaseList findById(String id);

    /**
     * 查询指定采购记录下的采购清单
     *
     * @param purchaseRecordId purchaseRecordId
     * @return Result
     */
    Result findByPurchaseRecordId(String purchaseRecordId);


    /**
     * 采购记录页，可以选择排序方式，也可以筛选关键词
     * @param pageId
     * @param pageSize
     * @param sortColumn
     * @param sortOrder
     * @param keyword
     * @return Result
     */
    Result findPage(Integer pageId, Integer pageSize, String sortColumn, String sortOrder, String keyword);

    /**
     * 修改指定采购清单的价格和数量
     *
     *
     * @param purchaseListVos @return int
     * @return
     */
    Result updatePriceAndNumber(List<PurchaseListVos> purchaseListVos);
}
