package com.firstGroup.restaurant.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OrderVo
 * @Description 创建订单时前台传过来的订单数据
 * @Author wuhaojie
 * @Date 2022/3/19 21:53
 */
@Data
public class OrderVo implements Serializable {

    private String cId;              //顾客id
    private BigDecimal allPrice;            //总价格
    private Integer dinnerNum;              //用餐人数
    private List<OrderMenuVo> orderMenuVoList;  //订单_菜品列表
    private String rId;            //餐馆id
    private String note;       //订单备注
}


