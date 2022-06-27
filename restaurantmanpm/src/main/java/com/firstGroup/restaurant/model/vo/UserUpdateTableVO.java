package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateTableVO {
    @ApiModelProperty(value = "ID", example = "0")
    private String fId;

    @ApiModelProperty(value = "状态", example = "0")
    private Integer fState;

    @ApiModelProperty(value = "实际用餐人数", example = "8")
    private Integer fRealnum;
}
