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
@ApiModel(value="员工", description="")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String eId;

    @ApiModelProperty(value = "餐厅ID", example = "0")
    private String rId;

    @ApiModelProperty(value = "姓名", example = "李四")
    private String eName;

    @ApiModelProperty(value = "电话", example = "13650068834")
    private String ePhone;

    @ApiModelProperty(value = "性别", example = "男")
    private Integer eSex;

    @ApiModelProperty(value = "工资", example = "1500.00")
    private BigDecimal eSalary;

    @ApiModelProperty(value = "工龄", example = "5")
    private Integer eWorktime;

    @ApiModelProperty(value = "职位", example = "服务员")
    private String eJob;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;
}
