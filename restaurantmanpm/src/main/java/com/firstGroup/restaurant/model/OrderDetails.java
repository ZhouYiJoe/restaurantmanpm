package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName OrderDetails
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/4/9 18:44
 */
@Data
public class OrderDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String odId;

    @ApiModelProperty(value = "订单ID", example = "0")
    private String oId;

    @ApiModelProperty(value = "创建（消费）时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;

    @ApiModelProperty(value = "用餐人数", example = "3")
    private Integer number;

    @ApiModelProperty(value = "订单预览菜品图片", example = "")
    private String imgurl;

    @ApiModelProperty(value = "订单预览菜品名字", example = "")
    private String name;

    @ApiModelProperty(value = "订单备注", example = "1238843327436247123")
    private String note;
}
