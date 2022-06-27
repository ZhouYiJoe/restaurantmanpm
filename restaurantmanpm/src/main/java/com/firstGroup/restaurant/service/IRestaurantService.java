package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Restaurant;

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
public interface IRestaurantService extends IService<Restaurant> {

    /**
     * 添加
     *
     * @param restaurant 
     * @return int
     */
    int add(Restaurant restaurant);

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
     * @param restaurant 
     * @return int
     */
    int updateData(Restaurant restaurant);

    /**
     * id查询数据
     *
     * @param id id
     * @return Restaurant
     */
    Restaurant findById(String id);

    /**
     * 查询所有数据
     *
     * @param
     * @return Restaurant
     */
    List<Restaurant> findAll();

    int getTotalNum();

    List<Map<String, Object>> getNumInEachArea();
}
