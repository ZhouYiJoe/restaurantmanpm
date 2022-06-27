package com.firstGroup.restaurant.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author outrun
 * @date 2022/5/24
 * @apiNote
 */
@Data
public class AdvicePageVo {

    @ApiModelProperty(value = "建议Id", example = "0")
    @TableId
    private String adviceId;

    @ApiModelProperty(value = "顾客昵称", example = "玛丽")
    private String cName;

    @ApiModelProperty(value = "订单ID", example = "0")
    private String oId;

    @ApiModelProperty(value = "下单时间", example = "2022-03-18 17:45:00")
    private Date orderTime;

    @ApiModelProperty(value = "建议内容", example = "这是一条建议")
    private String content;

    @ApiModelProperty(value = "投诉时间", example = "2022-03-18 17:45:00")
    private Date createTime;

    @ApiModelProperty(value = "建议主题", example = "菜品问题")
    private String name;

    @ApiModelProperty(value = "处理状态", example = "0")
    private Integer status;

    @ApiModelProperty(value = "星级", example = "0")
    private Integer star;
}
