package com.firstGroup.restaurant.model;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="材料-进货商关系", description="")
public class McBuyer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", example = "0")
    @TableId
    private String id;

    @ApiModelProperty(value = "材料ID", example = "0")
    private String mcId;

    @ApiModelProperty(value = "进货商ID", example = "0")
    private String bId;

}
