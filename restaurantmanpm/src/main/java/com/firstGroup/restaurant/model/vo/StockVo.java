package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName StockVo
 * @Description TODO
 * @Author Haojie
 * @Date 2022/5/8 20:20
 */
@Data
public class StockVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库存", example = "500")
    private BigDecimal sStock;

    @ApiModelProperty(value = "材料ID", example = "0")
    private String mcId;

    @ApiModelProperty(value = "餐厅ID", example = "1238843327436247123")
    private String rId;

    @ApiModelProperty(value = "名称", example = "白菜")
    private String mcName;

    @ApiModelProperty(value = "菜品分类ID", example = "0")
    private String cateId;

    @ApiModelProperty(value = "单位", example = "kg")
    private String mcUnit;

    @ApiModelProperty(value = "阈值，当材料的库存低于阈值时，则需要进行采购", example = "100.00")
    private BigDecimal mcThreshold;

    @ApiModelProperty(value = "描述", example = "这是一段描述")
    private String mcDescription;

    @ApiModelProperty(value = "图片URL", example = "https://img.szu.com/12")
    private String mcImgurl;

    @ApiModelProperty(value = "单价", example = "3.00")
    private BigDecimal mcPriceperunit;

    @ApiModelProperty(value = "充裕度：库存/阈值", example = "100.00")
    private BigDecimal abundant;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    private Date modifyTime;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    private Date createTime;
}
