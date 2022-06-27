package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Order;
import com.firstGroup.restaurant.model.vo.OrderVo;
import com.firstGroup.restaurant.model.vo.OrderVo1;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IOrderService extends IService<Order> {
    /**
     * 按照顾客id降序查询订单列表
     *
     * @param customer_id
     * @return List<OrderVo1>
     */
    List<OrderVo1> findForCustomer(String customer_id);

    /**
     * 创建订单
     *
     * @param orderVo
     * @return String
     */
    String createOrder(OrderVo orderVo) throws Exception;

    /**
     * 修改
     *
     * @param order 
     * @return int
     */
    int updateData(Order order);

    /**
     * id查询数据
     *
     * @param id id
     * @return Order
     */
    Order findById(String id);

    /**
     * 按照顾客id和餐馆id降序分页查询订单列表
     *
     * @param
     * @return
     */
    void findCustomerOrderByRestaurantId(Page<Order> pageParam,String customer_id, String r_id);

    /**
     * 回退订单数据
     *
     * @param
     * @return
     */
    void rollbackOrder(String oId);

    int getNum(String restaurantId, Date from, Date to);

    BigDecimal getSum(String restaurantId, Date from, Date to);

    Object findPage(Integer pageId, Integer pageSize, String r_id, String sortColumn, String sortOrder, String keyword, Date from, Date to);
}
