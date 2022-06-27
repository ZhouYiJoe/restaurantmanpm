package com.firstGroup.restaurant.model.vo;

import com.firstGroup.restaurant.model.PurchaseList;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author outrun
 * @date 2022/5/21
 * @apiNote
 */
@Data
public class PurchaseRecordVo implements Serializable {
    private String aId;
    private String rId;
    private List<PurchaseListVo> purchaseListVos;
}
