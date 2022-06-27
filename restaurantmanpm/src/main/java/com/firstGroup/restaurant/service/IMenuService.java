package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Menu;
import com.firstGroup.restaurant.model.dto.AppGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface IMenuService extends IService<Menu> {
    boolean exists(String id);
}
