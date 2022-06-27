package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.McBuyer;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-24
 */
public interface IMcBuyerService extends IService<McBuyer> {

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<McBuyer>
     */
    IPage<McBuyer> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param mcBuyer 
     * @return int
     */
    int add(McBuyer mcBuyer);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改
     *
     * @param mcBuyer 
     * @return int
     */
    int updateData(McBuyer mcBuyer);

    /**
     * id查询数据
     *
     * @param id id
     * @return McBuyer
     */
    McBuyer findById(Long id);
}
