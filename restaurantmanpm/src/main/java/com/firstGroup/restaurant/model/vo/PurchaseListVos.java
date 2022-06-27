package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author outrun
 * @date 2022/5/28
 * @apiNote
 */
@Data
public class PurchaseListVos {

    @ApiModelProperty(value = "ID", example = "0")
    private String purchaseListId;

    @ApiModelProperty(value = "实际单价", example = "1000")
    private BigDecimal mcActualPrice;

    @ApiModelProperty(value = "实际采购量", example = "500")
    private BigDecimal mcActualNumber;
}
