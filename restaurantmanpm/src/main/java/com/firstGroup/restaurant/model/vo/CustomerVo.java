package com.firstGroup.restaurant.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CustomerVo
 * @Description TODO
 * @Author wuhaojie
 * @Date 2022/3/28 17:01
 */
@Data
public class CustomerVo implements Serializable {
    private String cName;
    private String cAvatarUrl;
}
