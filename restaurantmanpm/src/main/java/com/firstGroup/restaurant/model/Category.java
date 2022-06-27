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
@ApiModel(value="分类", description="cateType: 0-菜品分类，1-物资分类")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String cateId;

    @ApiModelProperty(value = "名称", example = "粤菜")
    private String cateName;

    @ApiModelProperty(value = "描述", example = "广东的特色菜")
    private String cateDescription;

    @ApiModelProperty(value = "分类所属，0表示菜品分类，1表示配料分类", example = "1")
    private Integer cateType;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;
}
