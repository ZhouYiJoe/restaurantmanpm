package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.McBuyerMapper;
import com.firstGroup.restaurant.model.McBuyer;
import com.firstGroup.restaurant.service.IMcBuyerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-24
 */
@Service
public class McBuyerServiceImpl extends ServiceImpl<McBuyerMapper, McBuyer> implements IMcBuyerService {

    @Override
    public  IPage<McBuyer> findListByPage(Integer page, Integer pageCount){
        IPage<McBuyer> wherePage = new Page<>(page, pageCount);
        McBuyer where = new McBuyer();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(McBuyer mcBuyer){
        return baseMapper.insert(mcBuyer);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(McBuyer mcBuyer){
        return baseMapper.updateById(mcBuyer);
    }

    @Override
    public McBuyer findById(Long id){
        return  baseMapper.selectById(id);
    }
}
