package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.*;
import com.firstGroup.restaurant.model.*;
import com.firstGroup.restaurant.model.dto.AppGrantedAuthority;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.model.vo.AdminVo;
import com.firstGroup.restaurant.service.IAdminService;
import com.firstGroup.restaurant.service.IRedisService;
import com.firstGroup.restaurant.utils.JsonUtil;
import com.firstGroup.restaurant.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuRoleMapper menuRoleMapper;
    @Autowired
    private MenuTypeMapper menuTypeMapper;

    @Override
    public int add(Admin admin) {
        return baseMapper.insert(admin);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Admin admin) {
        return baseMapper.updateById(admin);
    }

    @Override
    public Admin findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<String> login(String username, String password) {
        //查找用户名和密码，并比对密码
        LambdaQueryWrapper<Admin> cond = new LambdaQueryWrapper<>();
        cond.eq(Admin::getAAccount, username);
        Admin admin = baseMapper.selectOne(cond);
        if (admin == null)
            return null;
        if (!passwordEncoder.matches(password, admin.getAPassword()))
            return null;
        //查询权限
        List<Role> roles = findRoleForAdmin(admin.getAId());
        UserType userType = UserType.ADMIN;
        for (Role role : roles) {
            if ("super_admin".equals(role.getKey())) {
                userType = UserType.SUPER_ADMIN;
                break;
            }
        }
        Collection<AppGrantedAuthority> authorities = selectAuthoritiesForRoles(roles);
        //把登录状态保存到Redis中
        //登录状态保存为hash类型，里面保存token和用户信息
        AppUserDetails userDetails = new AppUserDetails(
                admin.getAId(), null, authorities, userType);
        String token = JwtUtil.generateToken(admin.getAId());
        String key = IRedisService.concatKey("user_details", admin.getAId());
        Map<String, String> hash = new HashMap<>();
        hash.put("token", token);
        hash.put("user_details", JsonUtil.toJson(userDetails));
        redisService.putAll(key, hash);
        redisService.expire(key, 1, TimeUnit.DAYS);
        return Arrays.asList(token, admin.getRId(), admin.getAPermission().toString());
    }

    @Override
    public Collection<AppGrantedAuthority> selectAuthoritiesForRoles(List<Role> roles) {
        List<String> roleIdList = roles
                .stream().map(Role::getId).collect(Collectors.toList());
        QueryWrapper<MenuRole> cond2 = new QueryWrapper<>();
        cond2.in("role_id", roleIdList).select("distinct menu_id");
        List<MenuRole> menuRoles = menuRoleMapper.selectList(cond2);

        LambdaQueryWrapper<Menu> cond3 = new LambdaQueryWrapper<>();
        List<String> menuIdList = menuRoles
                .stream().map(MenuRole::getMenuId).collect(Collectors.toList());
        if (menuIdList.isEmpty()) return Collections.emptyList();
        cond3.in(Menu::getId, menuIdList)
                .select(Menu::getKey, Menu::getTypeId);
        List<Menu> menus = menuMapper.selectList(cond3);

        List<AppGrantedAuthority> authorities = new ArrayList<>();
        for (Menu menu : menus) {
            LambdaQueryWrapper<MenuType> cond4 = new LambdaQueryWrapper<>();
            cond4.eq(MenuType::getId, menu.getTypeId()).select(MenuType::getKey);
            MenuType menuType = menuTypeMapper.selectOne(cond4);
            AppGrantedAuthority authority =
                    new AppGrantedAuthority(menuType.getKey() + ":" + menu.getKey());
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public AdminVo getInfo(String token) {
        String userId = JwtUtil.getUserId(token);
        if (userId == null) {
            return null;
        }

        Admin admin = baseMapper.selectById(userId);
        AdminVo adminVo = new AdminVo();
        adminVo.setName(admin.getAAccount());
        adminVo.setRoles(admin.getAPermission() == 0 ? "[admin]" : "[manager]");
        adminVo.setAvatar(admin.getAvatar());
        adminVo.setAId(admin.getAId());
        return adminVo;
    }

    /**
     * 检测ID对应的管理员是否存在
     */
    @Override
    public boolean exists(String id) {
        LambdaQueryWrapper<Admin> cond = new LambdaQueryWrapper<>();
        cond.eq(Admin::getAId, id);
        return baseMapper.selectCount(cond) > 0;
    }

    /**
     * 查询管理员所具有的所有角色
     */
    @Override
    public List<Role> findRoleForAdmin(String adminId) {
        LambdaQueryWrapper<AdminRole> cond1 = new LambdaQueryWrapper<>();
        cond1.eq(AdminRole::getAdminId, adminId).select(AdminRole::getRoleId);
        List<AdminRole> adminRoles = adminRoleMapper.selectList(cond1);
        List<String> roleIdList = adminRoles
                .stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        LambdaQueryWrapper<Role> cond2 = new LambdaQueryWrapper<>();
        cond2.in(Role::getId, roleIdList).select(Role::getKey, Role::getId);
        return roleMapper.selectList(cond2);
    }


    @Override
    public Boolean validatePassword(String aId, String password) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getAId, aId);
        Admin admin = baseMapper.selectOne(wrapper);
        if(admin==null){
            return false;
        }
        if (!passwordEncoder.matches(password, admin.getAPassword()))
            return false;
        return true;
    }
}
