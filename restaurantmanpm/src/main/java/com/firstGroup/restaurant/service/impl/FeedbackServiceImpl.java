package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.FeedbackMapper;
import com.firstGroup.restaurant.model.Admin;
import com.firstGroup.restaurant.model.Feedback;
import com.firstGroup.restaurant.service.IFeedbackService;
import com.firstGroup.restaurant.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-22
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private ISecurityService securityService;

    @Override
    public  IPage<Feedback> findListByPage(Integer page, Integer pageCount){
        IPage<Feedback> wherePage = new Page<>(page, pageCount);
        Feedback where = new Feedback();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Feedback feedback){
        return baseMapper.insert(feedback);
    }

    @Override
    public int delete(String id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Feedback feedback){
        return baseMapper.updateById(feedback);
    }

    @Override
    public Feedback findById(String id){
        return  baseMapper.selectById(id);
    }

    @Override
    public Result findByAdviceId(String adviceId) {
        QueryWrapper<Feedback> feedbackQueryWrapper = new QueryWrapper<>();
        feedbackQueryWrapper.eq("advice_id", adviceId);
        Feedback feedback = baseMapper.selectOne(feedbackQueryWrapper);
        if (feedback == null)
            return Result.error().message("指定的反馈不存在");
        if (!securityService.isAccessible(feedback))
            return Result.error().message("无权访问");
        Admin admin = adminService.findById(feedback.getAId());
        Map<String, Object> res = new HashMap<>();
        res.put("content", feedback.getContent());
        res.put("createTime", feedback.getCreateTime());
        res.put("adminName", admin.getAName());
        return Result.ok().data(res);
    }
}
