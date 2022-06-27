package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.MenuTypeMapper;
import com.firstGroup.restaurant.model.MenuType;
import com.firstGroup.restaurant.service.IMenuTypeService;
import org.springframework.stereotype.Service;

@Service
public class MenuTypeServiceImpl extends ServiceImpl<MenuTypeMapper, MenuType>
        implements IMenuTypeService {
    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<MenuType> cond = new LambdaQueryWrapper<>();
        cond.eq(MenuType::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }
}
