package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.BuyerMapper;
import com.firstGroup.restaurant.mapper.McBuyerMapper;
import com.firstGroup.restaurant.mapper.StockMapper;
import com.firstGroup.restaurant.model.Buyer;
import com.firstGroup.restaurant.model.MaterialCode;
import com.firstGroup.restaurant.model.McBuyer;
import com.firstGroup.restaurant.model.Stock;
import com.firstGroup.restaurant.model.vo.StockBuyerVo;
import com.firstGroup.restaurant.model.vo.StockVo;
import com.firstGroup.restaurant.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {


    @Autowired
    private MaterialCodeServiceImpl materialCodeService;

    @Autowired
    StockMapper stockMapper;
    @Autowired
    McBuyerMapper mcBuyerMapper;
    @Autowired
    BuyerMapper buyerMapper;

    @Override
    public int add(Stock stock) {
        return baseMapper.insert(stock);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Stock stock) {
        return baseMapper.updateById(stock);
    }

    @Override
    public Stock findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Result getInRestaurant(String restaurantId) {
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("r_id", restaurantId);
        List<Stock> stocks = baseMapper.selectList(wrapper);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Stock stock : stocks) {
            MaterialCode mc = materialCodeService.findById(stock.getMcId());
            Map<String, Object> map = new HashMap<>();
            map.put("name", mc.getMcName());
            map.put("stock", stock.getSStock());
            map.put("threshold", mc.getMcThreshold());
            map.put("unit", mc.getMcUnit());
            map.put("abundant", stock.getSStock().divide(mc.getMcThreshold(), 4));
            list.add(map);
        }
        list.sort(Comparator.comparing(o -> ((BigDecimal) o.get("abundant"))));
        return Result.ok().data("rows", list);
    }

    @Override
    public Result findBelowThreshold(Integer pageId,
                                     Integer pageSize,
                                     String r_id,
                                     String cateId,
                                     String sortColumn,
                                     String sortOrder,
                                     Integer belowThreshold,
                                     String keyword) {
        List<StockVo> stockVos = baseMapper.findBelowThresholdPage((pageId - 1) * pageSize, pageSize, r_id, cateId,sortColumn, sortOrder, belowThreshold, keyword);

        Integer total = baseMapper.findBelowThresholdPageTotal(r_id, cateId, belowThreshold, keyword);

        return Result.ok().data("rows",stockVos).data("total",total);
    }

    @Override
    public List<StockBuyerVo> findAllBelowThreshold(String r_id) {
        List<StockVo> stockVoList = baseMapper.findAllBelowThreshold(r_id);
        List<StockBuyerVo> stockBuyerVoList = new ArrayList<>();
        for(StockVo sv: stockVoList){
            QueryWrapper<McBuyer> mcBuyerQueryWrapper = new QueryWrapper<>();
            mcBuyerQueryWrapper.eq("mc_id", sv.getMcId());
            List<McBuyer> mcBuyerList = mcBuyerMapper.selectList(mcBuyerQueryWrapper);
            StockBuyerVo sbVo = new StockBuyerVo();
            sbVo.setStockVo(sv);
            List<Buyer> buyerList = new ArrayList<>();
            for (McBuyer mcb: mcBuyerList){
                QueryWrapper<Buyer> buyerQueryWrapper = new QueryWrapper<>();
                buyerQueryWrapper.eq("b_id", mcb.getBId());
                buyerList.add(buyerMapper.selectOne(buyerQueryWrapper));
            }
            sbVo.setBuyerList(buyerList);
            stockBuyerVoList.add(sbVo);
        }
        return stockBuyerVoList;
    }
}
