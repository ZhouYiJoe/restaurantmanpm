package com.firstGroup.restaurant.model.vo;

import com.firstGroup.restaurant.model.Dish;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CategoryVo
 * @Description 点餐小程序首页返回的对象
 * @Author wuhaojie
 * @Date 2022/4/17 9:39
 */

@Data
public class DishListVo implements Serializable {
    private String cateId;
    private String cateName;
    private List<Dish> dishList;
}
