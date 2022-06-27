package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("角色")
@TableName("acl_role")
public class Role {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(name = "编号", example = "0")
    private String id;
    @ApiModelProperty(name = "名称", example = "菜品信息修改")
    private String name;
    @ApiModelProperty(name = "键", example = "modify")
    @TableField("`key`")
    private String key;
    @ApiModelProperty(name = "描述", example = "-")
    private String description;
    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
