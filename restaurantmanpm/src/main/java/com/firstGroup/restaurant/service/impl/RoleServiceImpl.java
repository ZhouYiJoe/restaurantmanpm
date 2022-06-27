package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.RoleMapper;
import com.firstGroup.restaurant.model.Role;
import com.firstGroup.restaurant.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    /**
     * 检查ID对应的角色是否存在
     */
    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<Role> cond = new LambdaQueryWrapper<>();
        cond.eq(Role::getId, id);
        return baseMapper.selectCount(cond) > 0;
    }
}
