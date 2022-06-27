package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.MenuMapper;
import com.firstGroup.restaurant.mapper.MenuRoleMapper;
import com.firstGroup.restaurant.mapper.MenuTypeMapper;
import com.firstGroup.restaurant.mapper.RoleMapper;
import com.firstGroup.restaurant.model.Menu;
import com.firstGroup.restaurant.model.MenuRole;
import com.firstGroup.restaurant.model.MenuType;
import com.firstGroup.restaurant.model.Role;
import com.firstGroup.restaurant.model.dto.AppGrantedAuthority;
import com.firstGroup.restaurant.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuRoleMapper menuRoleMapper;
    @Autowired
    private MenuTypeMapper menuTypeMapper;

    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<Menu> cond = new LambdaQueryWrapper<>();
        cond.eq(Menu::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }
}
