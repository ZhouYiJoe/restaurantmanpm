package com.firstGroup.restaurant.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.firstGroup.restaurant.model.Buyer;
import com.firstGroup.restaurant.model.MaterialCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MaterialCodeVo {
    private MaterialCode materialCode;
    private List<Buyer> buyerList;
}
