package com.firstGroup.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Admin;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.AdminVo;
import com.firstGroup.restaurant.model.vo.Authentication;
import com.firstGroup.restaurant.service.IAdminService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Api(tags = {"管理员数据"})
@RestController
@RequestMapping("/admin")
public class AdminController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /*
     *
     * 第六次迭代接口
     *
     */
    @PreAuthorize("hasAuthority('admin:update')")
    @ApiOperation(value = "更新")
    @PutMapping
    public Result update(@RequestBody Admin admin) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() == UserType.ADMIN)
            if (!userDetails.getUsername().equals(admin.getAId()))
                return Result.error().message("无权访问");
        admin.setAPassword(passwordEncoder.encode(admin.getAPassword()));
        adminService.updateData(admin);
        return Result.ok();
    }

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('admin:query')")
    @ApiOperation(value = "验证旧密码是否正确")
    @GetMapping("/validatePassword")
    public Result validatePassword(
            @ApiParam(value = "管理员id", required = true, example = "0")
            @RequestParam String aId,
            @ApiParam(value = "管理员密码", required = true, example = "5")
            @RequestParam String password) {
        Boolean b = adminService.validatePassword(aId, password);
        return b == true ? Result.ok() : Result.error().message("旧密码输入错误");

    }

    @PreAuthorize("hasAuthority('admin:add')")
    @ApiOperation(value = "新增")
    @PostMapping()
    public Result add(@RequestBody Admin admin) {
        admin.setAPassword(passwordEncoder.encode(admin.getAPassword()));
        adminService.add(admin);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('admin:query')")
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result findPage(
            @ApiParam(value = "页号", required = true, example = "0")
            @RequestParam @Min(0) Long pageId,
            @ApiParam(value = "每页记录数", required = true, example = "5")
            @RequestParam @Min(1) Long pageSize,
            @ApiParam(value = "查询关键字", example = "XXX")
            @RequestParam(required = false) String keyword) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        QueryWrapper<Admin> cond = new QueryWrapper<>();
        if (keyword != null) cond.like("a_account", keyword);
        return Result.ok().data("page", adminService.page(new Page<>(pageId, pageSize), cond));
    }

    /*
     *
     * 第三次迭代接口
     *
     */

    @ApiOperation(value = "后台登录")
    @PostMapping("login")
    public Result login(
            @ApiParam(value = "登录的账号密码", required = true)
            @RequestBody Authentication authentication) {
        String username = authentication.getUsername();
        String password = authentication.getPassword();
        List<String> info = adminService.login(username, password); //info是{token,rId}
        if (info == null) {
            return Result.error().message("账号密码不匹配！");
        }
        return Result.ok().data("token", info.get(0)).data("rId", info.get(1)).data("permission", info.get(2));
    }

    @PreAuthorize("hasAuthority('admin:auth')")
    @ApiOperation(value = "后台获取登录信息")
    @GetMapping("info")
    public Result info(
            @ApiParam(value = "登录成功后返回的token信息", required = true)
            @RequestHeader String token) {
        AdminVo adminVo = adminService.getInfo(token);
        if (adminVo == null) {
            return Result.error().message("无效的token！");
        }
        return Result.ok().data("roles", adminVo.getRoles()).data("name", adminVo.getName()).data("avatar", adminVo.getAvatar()).data("aId", adminVo.getAId());
    }



    /*
     *
     * 第一次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('admin:query')")
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageCount", value = "每页条数", dataTypeClass = Integer.class)
    })
    @GetMapping("findListByPage/{current}/{limit}")
    public Result findListByPage(@PathVariable Integer current,
                                 @PathVariable Integer limit) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() != UserType.SUPER_ADMIN)
            return Result.error().message("无权访问");
        Page<Admin> adminPage = new Page<>(current, limit);
        adminService.page(adminPage, null);
        long total = adminPage.getTotal();   //总记录数
        List<Admin> records = adminPage.getRecords(); //此页记录
        return Result.ok().data("total", total).data("rows", records);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id) {
        adminService.delete(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('admin:query')")
    @ApiOperation(value = "id查询")
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() == UserType.ADMIN)
            if (!userDetails.getUsername().equals(id))
                return Result.error().message("无权访问");
        Admin admin = adminService.findById(id);
        return Result.ok().data("admin", admin);
    }

}
