package com.firstGroup.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.firstGroup.restaurant.model.Stock;
import com.firstGroup.restaurant.model.vo.StockVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Repository
public interface StockMapper extends BaseMapper<Stock> {

    // 分页查询数据
    List<StockVo> findBelowThresholdPage(Integer begin, Integer pageSize, String r_id, String cateId, String sortColumn, String sortOrder, Integer belowThreshold, String keyword);

    // 获取数量
    Integer findBelowThresholdPageTotal(String r_id, String cateId, Integer belowThreshold, String keyword);

    // 获取所有阈值之下的数据
    List<StockVo> findAllBelowThreshold(String r_id);
}
