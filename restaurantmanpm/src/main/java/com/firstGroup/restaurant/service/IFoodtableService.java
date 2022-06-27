package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Foodtable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IFoodtableService extends IService<Foodtable> {

    /**
     * 添加
     *
     * @param foodtable 
     * @return int
     */
    int add(Foodtable foodtable);

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
     * @param foodtable 
     * @return int
     */
    int updateData(Foodtable foodtable);

    /**
     * id查询数据
     *
     * @param id id
     * @return Foodtable
     */
    Foodtable findById(String id);

    int getNumInRestaurant(String restaurantId);

    List<Map<String, Object>> getNumForEachState(String restaurantId);
}
