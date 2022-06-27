package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
 * @Author Haojie
 * @Description 后台管理添加菜品的Vo
 * @Date 2022-5-5
 **/
@Data
public class DishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "餐厅ID", example = "0")
    private String rId;

    @ApiModelProperty(value = "菜品分类ID", example = "0")
    private String cateId;

    @ApiModelProperty(value = "名称", example = "番茄炒鸡蛋")
    private String dName;

    @ApiModelProperty(value = "价格", example = "7.00")
    private BigDecimal dPrice;

    @ApiModelProperty(value = "描述", example = "这是一段描述")
    private String dDescribe;

    @ApiModelProperty(value = "图片URL", example = "https://img.szu.com/12")
    private String dImgurl;

    @ApiModelProperty(value = "配料列表", example = "")
    private List<DishMcVo> dishMcVos;

}
