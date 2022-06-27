package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.model.Admin;
import com.firstGroup.restaurant.model.Role;
import com.firstGroup.restaurant.model.dto.AppGrantedAuthority;
import com.firstGroup.restaurant.model.vo.AdminVo;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 添加
     *
     * @param admin
     * @return int
     */
    int add(Admin admin);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(String id);

    /**
     * 修改
     *
     * @param admin
     * @return int
     */
    int updateData(Admin admin);

    /**
     * id查询数据
     *
     * @param id id
     * @return Admin
     */
    Admin findById(String id);

    /**
     * 用户名密码验证并返回token
     *
     * @param username, password
     * @return token和rId组成的列表
     */
    List<String> login(String username, String password);

    /**
     * 根据token返回管理员信息
     *
     * @param token
     * @return AdminVo
     */
    AdminVo getInfo(String token);

    boolean exists(String id);

    List<Role> findRoleForAdmin(String adminId);

    Collection<AppGrantedAuthority> selectAuthoritiesForRoles(List<Role> roles);

    Boolean validatePassword(String aId, String password);
}
