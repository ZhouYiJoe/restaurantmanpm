package com.firstGroup.restaurant.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author outrun
 * @date 2022/5/21
 * @apiNote
 */
@Data
public class PurchaseListVo implements Serializable {
    private String bId;
    private String mcId;
    private BigDecimal expectedNumber;
    private BigDecimal expectedPrice;
}
