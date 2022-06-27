package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Category;
import com.firstGroup.restaurant.model.vo.DishListVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 添加
     *
     * @param category 
     * @return int
     */
    int add(Category category);

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
     * @param category 
     * @return int
     */
    int updateData(Category category);

    /**
     * id查询数据
     *
     * @param id id
     * @return Category
     */
    Category findById(String id);

    /**
     * 首页获取所有分类和菜品，包括优惠与热销
     *
     * @return List<DishListVo>
     */
    List<DishListVo> getAllInfo(String cId);

    /**
     * 获取所有菜品分类
     * @return 所有菜品分类
     */
    List<Category> findAllForDish();

    /**
     * 分页条件查询分类数据
     * @return 分类数据，以Page封装
     */
    IPage<Category> findPage(Integer pageId,
                                    Integer pageSize,
                                    Integer categoryType,
                                    String sortColumn,
                                    String sortOrder,
                                    String keyword);
}
