package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.RestaurantMapper;
import com.firstGroup.restaurant.model.Restaurant;
import com.firstGroup.restaurant.service.IRestaurantService;
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
public class RestaurantServiceImpl extends ServiceImpl<RestaurantMapper, Restaurant> implements IRestaurantService {

    @Override
    public int add(Restaurant restaurant) {
        return baseMapper.insert(restaurant);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Restaurant restaurant) {
        return baseMapper.updateById(restaurant);
    }

    @Override
    public Restaurant findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<Restaurant> findAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public int getTotalNum() {
        return baseMapper.selectCount(null);
    }

    @Override
    public List<Map<String, Object>> getNumInEachArea() {
        QueryWrapper<Restaurant> cond = new QueryWrapper<>();
        cond.groupBy("r_address")
                .select("r_address", "count(r_address) as count");
        return baseMapper.selectMaps(cond);
    }
}
