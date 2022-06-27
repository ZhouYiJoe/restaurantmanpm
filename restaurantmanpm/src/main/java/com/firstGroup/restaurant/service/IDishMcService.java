package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.DishMc;

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
public interface IDishMcService extends IService<DishMc> {
    List<DishMc> findForDish(String dishId);

    int delete(String mcId, String dId);

    DishMc findById(String mcId, String dId);

    /**
     * 添加
     *
     * @param dishMc 
     * @return int
     */
    int add(DishMc dishMc);

    /**
     * 修改
     *
     * @param dishMc 
     * @return int
     */
    int updateData(DishMc dishMc);

    /**
     * 获取所有菜品的配料
     * @return
     */
    List<Map<String, Object>> findAllForDish(String r_id);
}
