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
@ApiModel(value = "管理员")
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "ID", example = "0")
    private String aId;

    @ApiModelProperty(value = "餐厅ID", example = "0")
    private String rId;

    @ApiModelProperty(value = "账号", example = "1287444890")
    private String aAccount;

    @ApiModelProperty(value = "密码", example = "123456")
    private String aPassword;

    @ApiModelProperty(value = "姓名", example = "123456")
    private String aName;

    @ApiModelProperty(value = "昵称", example = "123456")
    private String aNickname;

    @ApiModelProperty(value = "权限", example = "1")
    private Integer aPermission;

    @ApiModelProperty(value = "创建时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT)    //自动插入
    private Date createTime;

    @ApiModelProperty(value = "更新时间", example = "2022-03-18 17:45:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)    //自动插入
    private Date modifyTime;

    @ApiModelProperty(value = "头像链接")
    private String avatar;
}
