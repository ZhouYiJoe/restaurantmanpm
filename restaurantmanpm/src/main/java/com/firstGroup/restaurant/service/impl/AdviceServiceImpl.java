package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.AdviceMapper;
import com.firstGroup.restaurant.model.*;
import com.firstGroup.restaurant.model.vo.AdviceFeedbackVo;
import com.firstGroup.restaurant.model.vo.AdvicePageVo;
import com.firstGroup.restaurant.service.IAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class AdviceServiceImpl extends ServiceImpl<AdviceMapper, Advice> implements IAdviceService {

    @Override
    public int add(Advice advice) {
        return baseMapper.insert(advice);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Advice advice) {
        return baseMapper.updateById(advice);
    }

    @Override
    public Advice findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Result findPage(Integer pageId, Integer pageSize, String r_id, String sortColumn, String sortOrder, Integer status, String keyword, Date from, Date to) {
        List<AdvicePageVo> list = baseMapper.findPage((pageId - 1) * pageSize, pageSize, r_id, sortColumn, sortOrder, status, keyword, from, to);
        Integer total = baseMapper.findRestaurantPageTotal(r_id, status, keyword, from, to);
        return Result.ok().data("rows", list).data("total", total);
    }

    @Override
    public int updateStatus(String id, Integer status) {
        UpdateWrapper<Advice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("status", status);
        return baseMapper.update(null, updateWrapper);
    }

    @Override
    public Result findNumberByDate(Date from, Date to, Integer star, String r_id) {
        Integer number = baseMapper.findRestaurantPageTotalByDate(r_id, star, from, to);
        Integer total = baseMapper.findRestaurantPageTotalByDate(r_id, null, from, to);
        return Result.ok().data("number", number).data("total", total);
    }

    @Override
    public Result findByCustomerId(Integer pageId, Integer pageSize, String r_id, String customerId) {
        List<AdviceFeedbackVo> list = baseMapper.findByCustomerId((pageId - 1) * pageSize, pageSize, r_id, customerId);
        Integer total = baseMapper.findPageTotalByCustomerId(r_id, customerId);
        return Result.ok().data("rows", list).data("total", total);
    }
}
