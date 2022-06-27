package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Feedback;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-22
 */
public interface IFeedbackService extends IService<Feedback> {

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Feedback>
     */
    IPage<Feedback> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param feedback 
     * @return int
     */
    int add(Feedback feedback);

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
     * @param feedback 
     * @return int
     */
    int updateData(Feedback feedback);

    /**
     * id查询数据
     *
     * @param id id
     * @return Feedback
     */
    Feedback findById(String id);

    /**
     * 建议id查询数据
     *
     * @param adviceId adviceId
     * @return Feedback
     */
    Result findByAdviceId(String adviceId);

}
