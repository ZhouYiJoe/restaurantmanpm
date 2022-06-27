package com.firstGroup.restaurant.controller;

import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.dto.AppGrantedAuthority;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.Menu;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.service.IMenuService;
import com.firstGroup.restaurant.service.IRedisService;
import com.firstGroup.restaurant.utils.JsonUtil;
import com.firstGroup.restaurant.utils.JwtUtil;
import com.firstGroup.restaurant.utils.WxUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@Api(tags = "账号相关")
public class AuthController {
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IMenuService menuService;

    /*
     *
     * 第五次迭代接口
     *
     */

    @PreAuthorize("hasAuthority('admin:auth')")
    @ApiOperation("管理员退出登录")
    @GetMapping("/adminLogout")
    public Result adminLogout() {
        //删除登录状态
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        String key = IRedisService.concatKey("user_details", userId);
        redisService.delete(key);
        return Result.ok();
    }

    @ApiOperation(value = "获取token", notes =
            "请求体包含一个code字段，其值为code的内容\n" +
                    "如果获取token成功，则响应体包含一个token字段，其值为token的内容"
    )
    @PostMapping("/token")
    public Result getToken(
            @ApiParam(value = "临时登录凭证code", required = true)
            @RequestBody Map<String, Object> reqBody) {
        String code = (String) reqBody.get("code");
        if (code == null) return Result.error().message("缺少code参数");

        try {
            Map<String, Object> map = WxUtil.code2Session(code);
            Integer errCode = (Integer) map.get("errcode");
            if (errCode == null) {
                String openId = (String) map.get("openid");
                String token = JwtUtil.generateToken(openId);
                //把登陆状态保存到Redis中，这时候还没有确定顾客的ID
                AppUserDetails userDetails = new AppUserDetails(
                        null, null, null, UserType.CUSTOMER);
                String key = IRedisService.concatKey("user_details", openId);
                redisService.put(key, "token", token);
                redisService.put(key, "user_details", JsonUtil.toJson(userDetails));
                redisService.expire(key, 1, TimeUnit.DAYS);
                return Result.ok().data("token", token);
            } else {
                return Result.error().message((String) map.get("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("未知错误");
        }
    }

    @ApiOperation(value = "验证token有效性", notes =
            "返回数据含有一个isValid字段，如果token有效，则isValid字段为true，否则为false"
    )
    @GetMapping("/checkToken")
    public Result checkToken(
            @ApiParam(value = "被验证的token", required = true)
            @RequestHeader("token") String token) {
        String userId = JwtUtil.getUserId(token);
        if (userId == null)
            return Result.ok().data("isValid", false);
        //检查token跟Redis中保存的登录状态中的token是否一致
        String key = IRedisService.concatKey("user_details", userId);
        String validToken = redisService.get(key, "token");
        return Result.ok().data("isValid", token.equals(validToken));
    }
}
