package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.MenuType;

public interface IMenuTypeService extends IService<MenuType> {
    boolean exists(String id);
}
