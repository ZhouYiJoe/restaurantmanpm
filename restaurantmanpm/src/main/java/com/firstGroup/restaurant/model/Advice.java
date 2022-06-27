package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("建议")
@Data
public class Advice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", example = "0")
    @TableId
    private String id;

    @ApiModelProperty(value = "订单ID", example = "0")
    private String oId;

    @ApiModelProperty(value = "建议内容", example = "这是一条建议")
    private String content;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;

    @ApiModelProperty(value = "建议主题", example = "菜品问题")
    private String name;

    @ApiModelProperty(value = "评价等级", example = "5")
    private Integer star;

    @ApiModelProperty(value = "处理状态", example = "0")
    private Integer status;

}
