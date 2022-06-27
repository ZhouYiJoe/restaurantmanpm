package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CategoryVo
 * @Description 添加category所需对象
 * @Author wuhaojie
 * @Date 2022/4/17 9:39
 */
@Data
public class CategoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称", example = "粤菜")
    private String cateName;

    @ApiModelProperty(value = "描述", example = "广东的特色菜")
    private String cateDescription;

    @ApiModelProperty(value = "分类所属", example = "1")
    private Integer cateType;
}
