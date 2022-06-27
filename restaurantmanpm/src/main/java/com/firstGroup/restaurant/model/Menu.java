package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

/**
 * 权限实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("权限")
@TableName("acl_permission")
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
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
    @ApiModelProperty(name = "权限所属类型的编号", example = "0")
    private String typeId;
    @ApiModelProperty(name = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(name = "修改时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
