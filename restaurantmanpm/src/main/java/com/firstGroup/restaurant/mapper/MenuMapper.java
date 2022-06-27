package com.firstGroup.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.firstGroup.restaurant.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> selectAuthorityForAdmin(String adminId);

    List<Menu> selectAuthorityForCustomer();

    List<Menu> selectPage(long offset, long pageSize, String keyword, String typeId);
}
