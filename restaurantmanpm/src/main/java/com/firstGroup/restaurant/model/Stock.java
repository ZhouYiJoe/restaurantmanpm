package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
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
@ApiModel(value="库存", description="")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库存", example = "500")
    private BigDecimal sStock;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String sId;

    @ApiModelProperty(value = "材料ID", example = "0000000000000000006")
    private String mcId;

    @ApiModelProperty(value = "餐厅ID", example = "1238843327436247123")
    private String rId;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;

    @ApiModelProperty(value = "版本号", example = "0")
    @Version
    private Integer version;
}
