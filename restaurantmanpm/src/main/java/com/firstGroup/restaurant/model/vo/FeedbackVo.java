package com.firstGroup.restaurant.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author outrun
 * @date 2022/5/22
 * @apiNote
 */
@Data
public class FeedbackVo implements Serializable {

    @ApiModelProperty(value = "反馈内容", example = "0")
    private String content;

    @ApiModelProperty(value = "管理员id", example = "0")
    private String aId;

    @ApiModelProperty(value = "建议id", example = "0")
    private String adviceId;
}
