package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Buyer;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IBuyerService extends IService<Buyer> {

    /**
     * 添加
     *
     * @param buyer 
     * @return int
     */
    int add(Buyer buyer);

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
     * @param buyer 
     * @return int
     */
    int updateData(Buyer buyer);

    /**
     * id查询数据
     *
     * @param id id
     * @return Buyer
     */
    Buyer findById(String id);
}
