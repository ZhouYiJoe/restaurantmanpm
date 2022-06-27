package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@ApiModel(value="顾客")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String cId;

    @ApiModelProperty(value = "数字id", example = "0")
    private Integer id;

    @ApiModelProperty(value = "餐桌ID", example = "0")
    private String fId;

    @ApiModelProperty(value = "昵称", example = "玛丽")
    private String cName;

    @ApiModelProperty(value = "头像url", example = "")
    @TableField("avatarUrl")
    private String cAvatarUrl;

    @ApiModelProperty(value = "电话", example = "13650068833")
    private String cPhone;

    @ApiModelProperty(value = "账号", example = "1287444899")
    private String cAccount;

    @ApiModelProperty(value = "密码", example = "123456")
    private String cPassword;

    @ApiModelProperty(value = "性别", example = "女")
    private Integer cSex;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;
}
