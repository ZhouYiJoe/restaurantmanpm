package com.firstGroup.restaurant.model.vo;

import com.firstGroup.restaurant.model.Buyer;
import lombok.Data;

import java.util.List;

@Data
public class StockBuyerVo {
    private StockVo stockVo;
    private List<Buyer> buyerList;
}
