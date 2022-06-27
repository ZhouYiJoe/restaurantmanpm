package com.firstGroup.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.firstGroup.restaurant.model.Order;
import com.firstGroup.restaurant.model.vo.DishNumber;
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
public interface OrderMapper extends BaseMapper<Order> {


    List<DishNumber> getUserOrderDishes(String cId);
}
