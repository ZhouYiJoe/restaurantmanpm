package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.MenuOrder;
import com.firstGroup.restaurant.model.vo.OrderDishVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IMenuOrderService extends IService<MenuOrder> {
    /**
     * 获取一个订单所包含的所有菜品
     * @param oId
     * @return
     */
    List<OrderDishVo> findForOrder(String oId);

    MenuOrder findById(String oId, String dId);

    int delete(String oId, String dId);

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<MenuOrder>
     */
    IPage<MenuOrder> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param menuOrder 
     * @return int
     */
    int add(MenuOrder menuOrder);

    /**
     * 修改
     *
     * @param menuOrder 
     * @return int
     */
    int updateData(MenuOrder menuOrder);
}
