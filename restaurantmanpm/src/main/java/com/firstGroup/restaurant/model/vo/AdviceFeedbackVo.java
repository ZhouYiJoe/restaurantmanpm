package com.firstGroup.restaurant.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author outrun
 * @date 2022/6/7
 * @apiNote
 */
@Data
public class AdviceFeedbackVo {
    @ApiModelProperty(value = "建议Id", example = "0")
    @TableId
    private String adviceId;

    @ApiModelProperty(value = "建议内容", example = "这是一条建议")
    private String adviceContent;

    @ApiModelProperty(value = "反馈内容", example = "这是一条反馈")
    private String feedbackContent;

    @ApiModelProperty(value = "订单ID", example = "0")
    private String oId;

    @ApiModelProperty(value = "建议主题", example = "菜品问题")
    private String name;

    @ApiModelProperty(value = "处理状态", example = "0")
    private Integer status;

    @ApiModelProperty(value = "评价等级", example = "5")
    private Integer star;

    @ApiModelProperty(value = "投诉时间", example = "2022-03-18 17:45:00")
    private Date createTime;
}
