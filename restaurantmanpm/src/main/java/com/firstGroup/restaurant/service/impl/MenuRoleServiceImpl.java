package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.MenuRoleMapper;
import com.firstGroup.restaurant.model.MenuRole;
import com.firstGroup.restaurant.service.IMenuRoleService;
import org.springframework.stereotype.Service;

@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole>
        implements IMenuRoleService {
}
