package com.firstGroup.restaurant.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CategoryVo1
 * @Description 更新category所需对象
 * @Author wuhaojie
 * @Date 2022/4/25 8:11
 */
@Data
public class CategoryVo1 implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String cateId;

    @ApiModelProperty(value = "名称", example = "粤菜")
    private String cateName;

    @ApiModelProperty(value = "描述", example = "广东的特色菜")
    private String cateDescription;

    @ApiModelProperty(value = "分类所属", example = "1")
    private Integer cateType;
}
