package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.FoodtableMapper;
import com.firstGroup.restaurant.model.Foodtable;
import com.firstGroup.restaurant.service.IFoodtableService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class FoodtableServiceImpl extends ServiceImpl<FoodtableMapper, Foodtable> implements IFoodtableService {

    @Override
    public int add(Foodtable foodtable) {
        return baseMapper.insert(foodtable);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Foodtable foodtable) {
        return baseMapper.updateById(foodtable);
    }

    @Override
    public Foodtable findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public int getNumInRestaurant(String restaurantId) {
        LambdaQueryWrapper<Foodtable> cond = new LambdaQueryWrapper<>();
        cond.eq(Foodtable::getRId, restaurantId);
        return baseMapper.selectCount(cond);
    }

    @Override
    public List<Map<String, Object>> getNumForEachState(String restaurantId) {
        QueryWrapper<Foodtable> cond = new QueryWrapper<>();
        cond.eq("r_id", restaurantId)
                .groupBy("f_state")
                .select("f_state", "count(f_state) as count");
        return baseMapper.selectMaps(cond);
    }
}
