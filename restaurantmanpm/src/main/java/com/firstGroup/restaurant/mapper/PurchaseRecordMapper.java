package com.firstGroup.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.firstGroup.restaurant.model.PurchaseRecord;
import com.firstGroup.restaurant.model.vo.AdvicePageVo;
import com.firstGroup.restaurant.model.vo.PurchaseRecordPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-21
 */
@Mapper
public interface PurchaseRecordMapper extends BaseMapper<PurchaseRecord> {

    // 建议列表页，可以选择排序方式，也可以筛选关键词
    List<PurchaseRecordPageVo> findPage(Integer begin, Integer pageSize, String r_id, String sortColumn, String sortOrder, String keyword, Date from, Date to);

    // 获取数量
    Integer findRestaurantPageTotal(String r_id, String keyword, Date from, Date to);
}
