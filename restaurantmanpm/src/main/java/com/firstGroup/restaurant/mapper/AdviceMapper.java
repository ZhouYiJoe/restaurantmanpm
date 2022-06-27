package com.firstGroup.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.firstGroup.restaurant.model.Advice;
import com.firstGroup.restaurant.model.vo.AdviceFeedbackVo;
import com.firstGroup.restaurant.model.vo.AdvicePageVo;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
public interface AdviceMapper extends BaseMapper<Advice> {

    // 建议列表页，可以选择排序方式，也可以筛选关键词
    List<AdvicePageVo> findPage(Integer begin, Integer pageSize, String r_id, String sortColumn, String sortOrder, Integer status, String keyword, Date from, Date to);

    // 获取数量
    Integer findRestaurantPageTotal(String r_id, Integer status, String keyword, Date from, Date to);

    Integer findRestaurantPageTotalByDate(String r_id, Integer star, Date from, Date to);

    List<AdviceFeedbackVo> findByCustomerId(Integer begin, Integer pageSize, String r_id, String customerId);

    Integer findPageTotalByCustomerId(String r_id, String customerId);
}
