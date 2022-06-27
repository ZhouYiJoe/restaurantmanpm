package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.DishMapper;
import com.firstGroup.restaurant.mapper.DishMcMapper;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.DishMc;
import com.firstGroup.restaurant.model.vo.DishMcVo;
import com.firstGroup.restaurant.model.vo.DishVo;
import com.firstGroup.restaurant.model.vo.GetDishVo;
import com.firstGroup.restaurant.service.IDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

    @Autowired
    private DishMcMapper dishMcMapper;

    @Override
    public void add(DishVo dishVo) {

        // 添加菜品信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVo,dish);
        dish.setDSales(0);
        baseMapper.insert(dish);

        // 添加配料表信息
        for (DishMcVo dishMcVo : dishVo.getDishMcVos()) {
            DishMc dishMc = new DishMc();
            dishMc.setDId(dish.getDId());
            dishMc.setMcId(dishMcVo.getMcId());
            dishMc.setNumber(dishMcVo.getNumber());
            dishMcMapper.insert(dishMc);
        }
    }

    @Override
    public void
    updateData(GetDishVo dishVo) {
        // 添加菜品信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVo,dish);
        baseMapper.updateById(dish);

        QueryWrapper<DishMc> wrapper = new QueryWrapper<>();
        wrapper.eq("d_id", dish.getDId());
        List<DishMc> dishMcs = dishMcMapper.selectList(wrapper);
        // 删除原来的配料
        for(DishMc dis: dishMcs){
            dishMcMapper.deleteById(dis.getId());
        }

        // 添加配料表信息
        for (DishMcVo dishMcVo : dishVo.getDishMcVos()) {
            DishMc dishMc = new DishMc();
            BeanUtils.copyProperties(dishMcVo, dishMc);
            dishMc.setDId(dish.getDId());
            dishMcMapper.insert(dishMc);
        }
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Dish dish) {
        return baseMapper.updateById(dish);
    }

    @Override
    public GetDishVo findById(String id) {
        Dish dish = baseMapper.selectById(id);
        QueryWrapper<DishMc> dishMcQueryWrapper = new QueryWrapper<>();
        dishMcQueryWrapper.eq("d_id", dish.getDId());
        GetDishVo getDishVo = new GetDishVo();
        BeanUtils.copyProperties(dish, getDishVo);
        List<DishMcVo> dishMcVos = new ArrayList<>();
        List<DishMc> dishMcs = dishMcMapper.selectList(dishMcQueryWrapper);
        for(DishMc dishMc: dishMcs){
            DishMcVo dishMcVo = new DishMcVo();
            BeanUtils.copyProperties(dishMc, dishMcVo);
            dishMcVos.add(dishMcVo);
        }
        getDishVo.setDishMcVos(dishMcVos);
        return getDishVo;
    }

    @Override
    public List<Dish> findPreferDish(int limit) {
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("d_price").last("limit " + limit);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Dish> findHotSaleDish(int limit) {
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("d_sales").last("limit " + limit);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<Dish> findPage(Integer pageId,
                                Integer pageSize,
                                String restaurantId,
                                String categoryId,
                                String sortColumn,
                                String sortOrder,
                                String keyword) {
        QueryWrapper<Dish> cond = Wrappers.query();
        cond.eq("cate_id", categoryId).eq("r_id", restaurantId);
        if (keyword != null) cond.like("d_name", keyword);
        if (sortOrder.equals("asc")) cond.orderByAsc(sortColumn);
        else if (sortOrder.equals("desc")) cond.orderByDesc(sortColumn);
        return baseMapper.selectPage(new Page<>(pageId, pageSize), cond);
    }

    @Override
    public List<Dish> getSalesTopN(String restaurantId, Integer n) {
        LambdaQueryWrapper<Dish> cond = Wrappers.lambdaQuery();
        cond.eq(Dish::getRId, restaurantId)
                .orderByDesc(Dish::getDSales);
        cond.last("limit " + n);
        return baseMapper.selectList(cond);
    }

    @Transactional
    @Override
    public void increaseSales(String dishId, int increment) {
        Dish dish = baseMapper.selectById(dishId);
        dish.setDSales(dish.getDSales() + increment);
        baseMapper.updateById(dish);
    }
}
