package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.BuyerMapper;
import com.firstGroup.restaurant.model.Buyer;
import com.firstGroup.restaurant.service.IBuyerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class BuyerServiceImpl extends ServiceImpl<BuyerMapper, Buyer> implements IBuyerService {

    @Override
    public int add(Buyer buyer) {
        return baseMapper.insert(buyer);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Buyer buyer) {
        return baseMapper.updateById(buyer);
    }

    @Override
    public Buyer findById(String id) {
        return baseMapper.selectById(id);
    }
}
