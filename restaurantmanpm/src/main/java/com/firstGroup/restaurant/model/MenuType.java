package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("权限类型")
@TableName("acl_permission_type")
public class MenuType {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "编号", name = "0")
    private String id;
    @ApiModelProperty(value = "名称", name = "菜品管理")
    private String name;
    @ApiModelProperty(value = "描述", name = "-")
    private String description;
    @ApiModelProperty(value = "键", name = "dish")
    @TableField("`key`")
    private String key;
    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
