package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.mapper.EmployeeMapper;
import com.firstGroup.restaurant.model.Employee;
import com.firstGroup.restaurant.service.IEmployeeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Override
    public IPage<Employee> findListByPage(Integer page, Integer pageCount) {
        IPage<Employee> wherePage = new Page<>(page, pageCount);
        Employee where = new Employee();
        return baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Employee employee) {
        return baseMapper.insert(employee);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Employee employee) {
        return baseMapper.updateById(employee);
    }

    @Override
    public Employee findById(String id) {
        return baseMapper.selectById(id);
    }

    /**
     * @param restaurantId 若为null则查询所有餐厅中的员工数量
     */
    @Override
    public int getNum(String restaurantId) {
        LambdaQueryWrapper<Employee> cond = Wrappers.lambdaQuery();
        if (restaurantId != null)
            cond.eq(Employee::getRId, restaurantId);
        return baseMapper.selectCount(cond);
    }

    /**
     * @param restaurantId 若为null则查询所有餐厅中的员工数量
     */
    @Override
    public List<Map<String, Object>> getNumForEachPos(String restaurantId) {
        QueryWrapper<Employee> cond = Wrappers.query();
        if (restaurantId != null)
            cond.eq("r_id", restaurantId);
        cond.groupBy("e_job")
                .select("e_job", "count(e_job) as count");
        return baseMapper.selectMaps(cond);
    }
}
