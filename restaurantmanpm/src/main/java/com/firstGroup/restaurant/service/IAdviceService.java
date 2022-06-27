package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Advice;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IAdviceService extends IService<Advice> {

    /**
     * 添加
     *
     * @param advice 
     * @return int
     */
    int add(Advice advice);

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
     * @param advice 
     * @return int
     */
    int updateData(Advice advice);

    /**
     * id查询数据
     *
     * @param id id
     * @return Advice
     */
    Advice findById(String id);

    /**
     * 建议列表页，可以选择排序方式，也可以筛选关键词
     * @param pageId
     * @param pageSize
     * @param r_id
     * @param sortColumn
     * @param sortOrder
     * @param status
     * @param keyword
     * @return
     */
    Result findPage(Integer pageId, Integer pageSize, String r_id, String sortColumn, String sortOrder, Integer status, String keyword, Date from, Date to);

    /**
     * 修改建议状态
     *
     * @param id 主键
     * @return int
     */
    int updateStatus(String id, Integer status);

    /**
     * 创建采购记录
     *
     * @param from         如果为null，则不限定日期范围，查询所有数据
     * @param to           如果为null，则不限定日期范围，查询所有数据
     * @param star
     * @param r_id
     * @return String
     */
    Result findNumberByDate(Date from, Date to, Integer star, String r_id);

    /**
     * 顾客id查询数据
     *
     * @param customerId customerId
     * @return Result
     */
    Result findByCustomerId(Integer pageId, Integer pageSize, String r_id, String customerId);
}
