package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.CustomerMapper;
import com.firstGroup.restaurant.model.Customer;
import com.firstGroup.restaurant.model.vo.CustomerVo;
import com.firstGroup.restaurant.service.ICustomerService;
import com.vdurmont.emoji.EmojiParser;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Override
    public IPage<Customer> findListByPage(Integer page, Integer pageCount) {
        IPage<Customer> wherePage = new Page<>(page, pageCount);
        Customer where = new Customer();
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Customer customer) {
        return baseMapper.insert(customer);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Customer customer) {
        return baseMapper.updateById(customer);
    }

    @Override
    public Customer findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public String getCustomerId(CustomerVo customerVo) {
        String cId = null;
        //先根据用户名判断该用户存不存在
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        //转换，名字可能包含emoji表情
        String nameConvert = EmojiParser.parseToAliases(customerVo.getCName());
        wrapper.eq("c_name",nameConvert);
        Customer customer = baseMapper.selectOne(wrapper);  //这里默认用户昵称不一样
        if(customer==null){
            Customer customer1 = new Customer();
            customer1.setCName(nameConvert);
            customer1.setCAvatarUrl(customerVo.getCAvatarUrl());
            baseMapper.insert(customer1);
            cId = baseMapper.selectOne(wrapper).getCId();
        }else {
            cId = customer.getCId();
        }
        return cId;
    }
}
