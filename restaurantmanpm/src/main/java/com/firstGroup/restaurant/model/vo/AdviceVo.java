package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AdviceVo
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/4/1 10:31
 */
@Data
public class AdviceVo implements Serializable {

    @ApiModelProperty(value = "订单ID", example = "0")
    private String oId;

    @ApiModelProperty(value = "建议内容", example = "这是一条建议")
    private String content;

    @ApiModelProperty(value = "建议主题", example = "菜品问题")
    private String name;

    @ApiModelProperty(value = "评价等级", example = "5")
    private Integer star;
}
