package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.DishMapper;
import com.firstGroup.restaurant.mapper.DishMcMapper;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.DishMc;
import com.firstGroup.restaurant.service.IDishMcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
public class DishMcServiceImpl extends ServiceImpl<DishMcMapper, DishMc> implements IDishMcService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public List<DishMc> findForDish(String dishId) {
        LambdaQueryWrapper<DishMc> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DishMc::getDId, dishId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public int delete(String mcId, String dId) {
        LambdaQueryWrapper<DishMc> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DishMc::getMcId, mcId);
        wrapper.eq(DishMc::getDId, dId);
        return baseMapper.delete(wrapper);
    }

    @Override
    public DishMc findById(String mcId, String dId) {
        LambdaQueryWrapper<DishMc> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DishMc::getMcId, mcId);
        wrapper.eq(DishMc::getDId, dId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public int add(DishMc dishMc) {
        return baseMapper.insert(dishMc);
    }

    @Override
    public int updateData(DishMc dishMc) {
        return baseMapper.updateById(dishMc);
    }

    @Override
    public List<Map<String, Object>> findAllForDish(String r_id) {
        QueryWrapper<Dish> dishQueryWrapper = new QueryWrapper<Dish>();
        dishQueryWrapper.eq("r_id", r_id);
        List<Dish> dishList = dishMapper.selectList(dishQueryWrapper);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Dish dish : dishList){
            Map<String, Object> map = new HashMap<>();
            map.put("dish", dish);
            QueryWrapper<DishMc> dishMcQueryWrapper = new QueryWrapper<>();
            dishMcQueryWrapper.eq("d_id", dish.getDId());
            List<DishMc> dishMcList = baseMapper.selectList(dishMcQueryWrapper);
            map.put("mc", dishMcList);
            list.add(map);
        }
        return list;
    }
}
