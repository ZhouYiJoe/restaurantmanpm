package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.AdminRoleMapper;
import com.firstGroup.restaurant.model.AdminRole;
import com.firstGroup.restaurant.service.IAdminRoleService;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole>
        implements IAdminRoleService {
}
