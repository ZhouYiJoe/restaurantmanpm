package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Feedback;
import com.firstGroup.restaurant.model.PurchaseRecord;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.FeedbackVo;
import com.firstGroup.restaurant.service.IFeedbackService;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author firstGroup
 * @since 2022-05-22
 */
@Api(tags = "投诉反馈信息")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IFeedbackService feedbackService;
    @Autowired
    private ISecurityService securityService;

    /*
     *
     * 第六次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('feedback:query')")
    @ApiOperation(value = "建议id查询")
    @GetMapping("findByAdviceId/{adviceId}")
    public Result findByAdviceId(@PathVariable String adviceId){
        return feedbackService.findByAdviceId(adviceId);
    }

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('feedback:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody FeedbackVo feedbackVo){
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackVo, feedback);
        if (!securityService.isAccessible(feedback))
            return Result.error().message("无权访问");
        feedbackService.add(feedback);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('feedback:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id){
        Feedback feedback = feedbackService.findById(id);
        if (feedback == null)
            return Result.error().message("指定的反馈不存在");
        if (!securityService.isAccessible(feedback))
            return Result.error().message("无权访问");
        feedbackService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('feedback:update')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Result update(@RequestBody Feedback feedback){
        Feedback feedback1 = feedbackService.findById(feedback.getFId());
        if (feedback1 == null)
            return Result.error().message("指定的反馈不存在");
        if (!securityService.isAccessible(feedback1))
            return Result.error().message("无权访问");
        feedbackService.updateData(feedback);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('feedback:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页码", dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "pageCount", value = "每页条数", dataTypeClass = Integer.class)
    })
    @GetMapping()
    public Result findListByPage(@RequestParam Integer page,
                                   @RequestParam Integer pageCount){
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        Page<Feedback> purchaseRecordPage = new Page<>(page, pageCount);
        feedbackService.page(purchaseRecordPage, null);
        long total = purchaseRecordPage.getTotal();   //总记录数
        List<Feedback> records = purchaseRecordPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('feedback:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id){
        Feedback feedback = feedbackService.findById(id);
        if (feedback == null)
            return Result.error().message("指定的反馈不存在");
        if (!securityService.isAccessible(feedback))
            return Result.error().message("无权访问");
        return Result.ok().data("feedback", feedback);
    }
}
