package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.vo.DishVo;
import com.firstGroup.restaurant.model.vo.GetDishVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IDishService extends IService<Dish> {

    /**
     * 添加
     *
     * @param dishVo
     * @return int
     */
    void add(DishVo dishVo);

    /**
     * 更新
     *
     * @param dishVo
     * @return int
     */
    void updateData(GetDishVo dishVo);


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
     * @param dish 
     * @return int
     */
    int updateData(Dish dish);

    /**
     * id查询数据
     *
     * @param id id
     * @return Dish
     */
    GetDishVo findById(String id);

    /**
     * 查询优惠商品数据
     *
     * @param limit 查询条数
     * @return Dish列表
     */
    List<Dish> findPreferDish(int limit);

    /**
     * 查询热销商品数据
     *
     * @param limit 查询条数
     * @return Dish列表
     */
    List<Dish> findHotSaleDish(int limit);

    IPage<Dish> findPage(Integer pageId,
                         Integer pageSize,
                         String restaurantId,
                         String categoryId,
                         String sortColumn,
                         String sortOrder,
                         String keyword);

    List<Dish> getSalesTopN(String restaurantId, Integer n);

    void increaseSales(String dishId, int increment);
}
