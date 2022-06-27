package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DishMcVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "材料ID", example = "0")
    private String mcId;

    @ApiModelProperty(value = "材料用量", example = "5")
    private BigDecimal number;
}