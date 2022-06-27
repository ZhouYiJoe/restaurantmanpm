package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Stock;
import com.firstGroup.restaurant.model.vo.StockBuyerVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IStockService extends IService<Stock> {


    /**
     * 添加
     *
     * @param stock 
     * @return int
     */
    int add(Stock stock);

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
     * @param stock 
     * @return int
     */
    int updateData(Stock stock);

    /**
     * id查询数据
     *
     * @param id id
     * @return Stock
     */
    Stock findById(String id);

    /**
     *
     * @param restaurantId
     * @return map(名称, 剩余量)
     */
    Result getInRestaurant(String restaurantId);

    /**
     * 查找阈值之下的库存
     * @param pageId
     * @param pageSize
     * @param r_id
     * @param cateId
     * @param sortColumn
     * @param sortOrder
     * @param belowThreshold
     * @param keyword
     * @return
     */
    Result findBelowThreshold(Integer pageId, Integer pageSize, String r_id, String cateId, String sortColumn, String sortOrder, Integer belowThreshold, String keyword);


    /**
     * 查找所有阈值之下的库存
     * @return List<StockVo>
     */
    List<StockBuyerVo> findAllBelowThreshold(String r_id);
}
