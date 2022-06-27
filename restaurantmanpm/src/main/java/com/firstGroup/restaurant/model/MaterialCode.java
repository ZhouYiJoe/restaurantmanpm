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
@ApiModel(value="材料", description="")
public class MaterialCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String mcId;

    @ApiModelProperty(value = "菜品分类ID", example = "0")
    private String cateId;

    @ApiModelProperty(value = "名称", example = "白菜")
    private String mcName;

    @ApiModelProperty(value = "单位", example = "kg")
    private String mcUnit;

    @ApiModelProperty(value = "阈值，当材料的库存低于阈值时，则需要进行采购", example = "100.00")
    private BigDecimal mcThreshold;

    @ApiModelProperty(value = "描述", example = "这是一段描述")
    private String mcDescription;

    @ApiModelProperty(value = "图片URL", example = "https://img.szu.com/12")
    private String mcImgurl;

    @TableField("mc_pricePerUnit")
    @ApiModelProperty(value = "单价", example = "3.00")
    private BigDecimal mcPriceperunit;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;
}
