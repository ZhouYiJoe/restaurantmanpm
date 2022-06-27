package com.firstGroup.restaurant.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author outrun
 * @date 2022/5/24
 * @apiNote
 */
@Data
public class PurchaseRecordPageVo {

    @TableId
    @ApiModelProperty(value = "采购记录ID", example = "0")
    private String purchaseRecordId;

    @ApiModelProperty(value = "处理人姓名", example = "123456")
    private String aName;

    @ApiModelProperty(value = "备注", example = "这是一次采购")
    private String note;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    private Date modifyTime;

    @ApiModelProperty(value = "理论总价", example = "1000")
    private BigDecimal expectedTotalPrice;

    @ApiModelProperty(value = "实际总价", example = "1000")
    private BigDecimal actualTotalPrice;

    @ApiModelProperty(value = "采购是否完成", example = "0")
    private Integer isFinish;

}
