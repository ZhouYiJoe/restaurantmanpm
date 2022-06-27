package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.DishMapper;
import com.firstGroup.restaurant.mapper.MenuOrderMapper;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.MenuOrder;
import com.firstGroup.restaurant.model.vo.OrderDishVo;
import com.firstGroup.restaurant.service.IMenuOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

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
public class MenuOrderServiceImpl extends ServiceImpl<MenuOrderMapper, MenuOrder> implements IMenuOrderService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public List<OrderDishVo> findForOrder(String oId) {
        // 获取oId的订单菜品关系列表
        LambdaQueryWrapper<MenuOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuOrder::getOId, oId);
        List<MenuOrder> menuOrders = baseMapper.selectList(wrapper);

        // 返回的OrderDishVo对象
        List<OrderDishVo> orderDishes = new ArrayList<>();
        for (MenuOrder menuOrder : menuOrders) {
            OrderDishVo orderDish = new OrderDishVo();
            //根据菜品id查询菜品信息
            Dish dish = dishMapper.selectById(menuOrder.getDId());
            orderDish.setDId(menuOrder.getDId());       //菜品id
            orderDish.setNumber(menuOrder.getNumber()); //菜品数量
            orderDish.setDName(dish.getDName());        //菜品名称
            orderDish.setDPrice(dish.getDPrice());      //菜品价格
            orderDish.setDDescribe(dish.getDDescribe());//菜品描述
            orderDish.setDImgurl(dish.getDImgurl());    //菜品图片
            orderDishes.add(orderDish);
        }
        return orderDishes;
    }

    @Override
    public MenuOrder findById(String oId, String dId) {
        LambdaQueryWrapper<MenuOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuOrder::getDId, dId);
        wrapper.eq(MenuOrder::getOId, oId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public int delete(String oId, String dId) {
        LambdaQueryWrapper<MenuOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuOrder::getDId, dId);
        wrapper.eq(MenuOrder::getOId, oId);
        return baseMapper.delete(wrapper);
    }

    @Override
    public IPage<MenuOrder> findListByPage(Integer page, Integer pageCount) {
        IPage<MenuOrder> wherePage = new Page<>(page, pageCount);
        MenuOrder where = new MenuOrder();

        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(MenuOrder menuOrder) {
        return baseMapper.insert(menuOrder);
    }

    @Override
    public int updateData(MenuOrder menuOrder) {
        return baseMapper.updateById(menuOrder);
    }
}
