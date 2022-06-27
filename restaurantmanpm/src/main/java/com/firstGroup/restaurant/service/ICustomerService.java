package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Customer;
import com.firstGroup.restaurant.model.vo.CustomerVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface ICustomerService extends IService<Customer> {

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Customer>
     */
    IPage<Customer> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param customer 
     * @return int
     */
    int add(Customer customer);

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
     * @param customer 
     * @return int
     */
    int updateData(Customer customer);

    /**
     * id查询数据
     *
     * @param id id
     * @return Customer
     */
    Customer findById(String id);

    /**
     * 微信授权登录后，根据登录信息返回顾客id
     *
     * @param customerVo
     * @return Integer
     */
    String getCustomerId(CustomerVo customerVo);
}
