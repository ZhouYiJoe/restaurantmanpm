package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateCusVO {
    @ApiModelProperty(value = "ID", example = "0")
    private String cId;

    @ApiModelProperty(value = "餐桌ID", example = "0")
    private String fId;

    @ApiModelProperty(value = "昵称", example = "玛丽")
    private String cName;

    @ApiModelProperty(value = "头像url", example = "www.baidu.com/img.png")
    private String cAvatarUrl;

    @ApiModelProperty(value = "电话", example = "13650068833")
    private String cPhone;

    @ApiModelProperty(value = "密码", example = "123456")
    private String cPassword;

    @ApiModelProperty(value = "性别", example = "女")
    private Integer cSex;
}
