package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="采购清单", description="")
public class PurchaseList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String id;

    @ApiModelProperty(value = "采购记录ID", example = "0")
    private String prId;

    @ApiModelProperty(value = "进货商ID", example = "0")
    private String bId;

    @ApiModelProperty(value = "材料ID", example = "0")
    private String mcId;

    @ApiModelProperty(value = "实际单价", example = "1000")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "实际采购量", example = "500")
    private BigDecimal actualNumber;

    @ApiModelProperty(value = "预期单价", example = "1000")
    private BigDecimal expectedPrice;

    @ApiModelProperty(value = "预期采购量", example = "500")
    private BigDecimal expectedNumber;

    @ApiModelProperty(value = "创建（采购）时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;

}
