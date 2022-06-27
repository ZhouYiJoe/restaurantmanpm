package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.BuyerMapper;
import com.firstGroup.restaurant.mapper.MaterialCodeMapper;
import com.firstGroup.restaurant.mapper.McBuyerMapper;
import com.firstGroup.restaurant.model.*;
import com.firstGroup.restaurant.model.vo.MaterialCodeVo;
import com.firstGroup.restaurant.service.IMaterialCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class MaterialCodeServiceImpl extends ServiceImpl<MaterialCodeMapper, MaterialCode> implements IMaterialCodeService {

    @Autowired
    private McBuyerMapper mcBuyerMapper;

    @Autowired
    private BuyerMapper buyerMapper;

    @Override
    public int add(MaterialCode materialCode) {
        return baseMapper.insert(materialCode);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(MaterialCode materialCode) {
        return baseMapper.updateById(materialCode);
    }

    @Override
    public MaterialCode findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public IPage<MaterialCode> findPage(Integer pageId, Integer pageSize, String categoryId, String sortColumn, String sortOrder, String keyword) {
        QueryWrapper<MaterialCode> wrapper = new QueryWrapper<>();
        if (sortColumn != null) {
            if (sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    wrapper.orderByAsc(sortColumn);
                } else if (sortOrder.equals("desc")) {
                    wrapper.orderByDesc(sortColumn);
                }
            } else {
                wrapper.orderByDesc(sortColumn);
            }
        }
        if (categoryId != null) {
            wrapper.eq("cate_id", categoryId);
        }
        if (keyword != null) {
            wrapper.like("mc_name", keyword);
        }
        return baseMapper.selectPage(new Page<>(pageId, pageSize), wrapper);
    }

    @Override
    public List<MaterialCode> findAllNameAndUnit() {
        QueryWrapper<MaterialCode> wrapper = new QueryWrapper<>();
        wrapper.select("mc_name", "mc_unit");
        List<MaterialCode> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<MaterialCodeVo> findAll() {
        List<MaterialCode> materialCodeList = baseMapper.selectList(null);
        List<MaterialCodeVo> materialCodeVoList = new ArrayList<>();
        for(MaterialCode mc: materialCodeList){
            QueryWrapper<McBuyer> mcBuyerQueryWrapper = new QueryWrapper<>();
            mcBuyerQueryWrapper.eq("mc_id", mc.getMcId());
            List<McBuyer> mcBuyerList = mcBuyerMapper.selectList(mcBuyerQueryWrapper);
            MaterialCodeVo mcVo = new MaterialCodeVo();
            mcVo.setMaterialCode(mc);
            List<Buyer> buyerList = new ArrayList<>();
            for (McBuyer mcb: mcBuyerList){
                QueryWrapper<Buyer> buyerQueryWrapper = new QueryWrapper<>();
                buyerQueryWrapper.eq("b_id", mcb.getBId());
                buyerList.add(buyerMapper.selectOne(buyerQueryWrapper));
            }
            mcVo.setBuyerList(buyerList);
            materialCodeVoList.add(mcVo);
        }
        return materialCodeVoList;
    }
}
