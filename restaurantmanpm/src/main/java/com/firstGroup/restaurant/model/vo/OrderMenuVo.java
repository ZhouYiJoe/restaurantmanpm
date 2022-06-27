package com.firstGroup.restaurant.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName OrderMenu
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/3/19 22:36
 */
@Data
public class OrderMenuVo implements Serializable {
    private String dId;
    private Integer number;
}
