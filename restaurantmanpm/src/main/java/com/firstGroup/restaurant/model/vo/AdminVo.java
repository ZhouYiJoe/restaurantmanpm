package com.firstGroup.restaurant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AdminVo
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/4/17 10:24
 */
@Data
public class AdminVo {
    private String roles;

    @ApiModelProperty(value = "ID", example = "0")
    private String aId;

    @ApiModelProperty(value = "账号/用户名", example = "1287444890")
    private String name;

    @ApiModelProperty(value = "头像链接")
    private String avatar;
}
