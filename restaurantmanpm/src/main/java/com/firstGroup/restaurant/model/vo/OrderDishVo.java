package com.firstGroup.restaurant.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName OrderDishVo
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/4/7 11:44
 */
@Data
public class OrderDishVo implements Serializable {
    private String dId;
    private String dName;
    private BigDecimal dPrice;
    private Integer number;
    private String dDescribe;
    private String dImgurl;
    private String note;
}
