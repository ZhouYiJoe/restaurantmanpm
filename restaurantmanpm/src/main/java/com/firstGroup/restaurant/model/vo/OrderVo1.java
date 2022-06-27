package com.firstGroup.restaurant.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName OrderVo
 * @Description 查询订单列表返回的数据
 * @Author wuhaojie
 * @Date 2022/3/19 21:53
 */
@Data
public class OrderVo1 implements Serializable {

    private String oId;
    private String cId;
    private String rId;
    private BigDecimal oPrice;
    private Integer oState;
    private Integer number;
    private String imgurl;
    private String name;
    private String note;
    private Date createTime;
    private Date modifyTime;
}


