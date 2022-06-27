package com.firstGroup.restaurant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Employee;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Employee>
     */
    IPage<Employee> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param employee 
     * @return int
     */
    int add(Employee employee);

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
     * @param employee 
     * @return int
     */
    int updateData(Employee employee);

    /**
     * id查询数据
     *
     * @param id id
     * @return Employee
     */
    Employee findById(String id);

    /**
     * 获取员工数量
     * @param restaurantId
     * @return
     */
    int getNum(String restaurantId);

    /**
     * 获取各个职位员工数量
     * @param restaurantId
     * @return
     */
    List<Map<String, Object>> getNumForEachPos(String restaurantId);
}
