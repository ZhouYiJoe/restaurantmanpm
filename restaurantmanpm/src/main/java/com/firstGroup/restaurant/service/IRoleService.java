package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Role;

public interface IRoleService extends IService<Role> {
    boolean exists(String id);
}
