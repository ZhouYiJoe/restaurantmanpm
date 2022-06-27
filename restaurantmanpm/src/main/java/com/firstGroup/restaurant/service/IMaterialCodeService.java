package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.MaterialCode;
import com.firstGroup.restaurant.model.vo.MaterialCodeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IMaterialCodeService extends IService<MaterialCode> {

    /**
     * 添加
     *
     * @param materialCode 
     * @return int
     */
    int add(MaterialCode materialCode);

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
     * @param materialCode 
     * @return int
     */
    int updateData(MaterialCode materialCode);

    /**
     * id查询数据
     *
     * @param id id
     * @return MaterialCode
     */
    MaterialCode findById(String id);

    /**
     * 分页查询菜品，可指定分类、排序方式、模糊查询关键字
     * @param pageId
     * @param pageSize
     * @param r_id
     * @param categoryId
     * @param sortColumn
     * @param sortOrder
     * @param keyword
     * @return
     */
    IPage<MaterialCode> findPage(Integer pageId, Integer pageSize, String categoryId, String sortColumn, String sortOrder, String keyword);

    /**
     * 获取所有的配料名字和单位
     */
    List<MaterialCode> findAllNameAndUnit();

    /**
     * 获取所有配料及其进货商
     * @return
     */
    List<MaterialCodeVo> findAll();
}
