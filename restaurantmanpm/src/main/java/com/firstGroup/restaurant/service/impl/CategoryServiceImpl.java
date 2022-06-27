package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.CategoryMapper;
import com.firstGroup.restaurant.mapper.DishMapper;
import com.firstGroup.restaurant.model.Category;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.vo.DishListVo;
import com.firstGroup.restaurant.service.ICategoryService;
import com.firstGroup.restaurant.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private IDishService dishService;
    @Autowired
    private RecommendDishService recommendDishService;


    @Override
    public int add(Category category) {
        return baseMapper.insert(category);
    }

    @Override
    public int delete(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Category category) {
        return baseMapper.updateById(category);
    }

    @Override
    public Category findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<DishListVo> getAllInfo(String cId) {
        List<DishListVo> dishListVoList = new ArrayList<>();
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("cate_type", 0);
        List<Category> categoryList = baseMapper.selectList(wrapper);   //所有分类

        for (Category category : categoryList) {
            QueryWrapper<Dish> dishQueryWrapper = new QueryWrapper<>();
            dishQueryWrapper.eq("cate_id", category.getCateId());
            List<Dish> dishes = dishMapper.selectList(dishQueryWrapper);    //此分类所有菜品
            DishListVo dishListVo = new DishListVo();
            dishListVo.setCateId(category.getCateId());
            dishListVo.setCateName(category.getCateName());
            dishListVo.setDishList(dishes);
            dishListVoList.add(dishListVo);
        }

        // 添加推荐菜品
        /*DishListVo dishListVo = new DishListVo();
        dishListVo.setCateName("推荐");
        try {
            dishListVo.setDishList(recommendDishService.userCFRecommend(cId, 2, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dishListVoList.add(0, dishListVo);*/

        // 添加热销和折扣
        DishListVo dishListVo1 = new DishListVo();
        dishListVo1.setCateName("热销");
        dishListVo1.setDishList(dishService.findHotSaleDish(5));
        dishListVoList.add(1, dishListVo1);
        // 添加折扣
        DishListVo dishListVo2 = new DishListVo();
        dishListVo2.setCateName("折扣");
        dishListVo2.setDishList(dishService.findPreferDish(5));
        dishListVoList.add(2, dishListVo2);

        return dishListVoList;
    }

    @Override
    public List<Category> findAllForDish() {
        LambdaQueryWrapper<Category> cond = Wrappers.lambdaQuery();
        cond.eq(Category::getCateType, 0);
        return baseMapper.selectList(cond);
    }

    @Override
    public IPage<Category> findPage(Integer pageId,
                                    Integer pageSize,
                                    Integer categoryType,
                                    String sortColumn,
                                    String sortOrder,
                                    String keyword) {

        QueryWrapper<Category> cond = Wrappers.query();
        cond.eq("cate_type", categoryType);
        if (keyword != null) cond.like("cate_name", keyword);
        if (sortOrder.equals("asc")) cond.orderByAsc(sortColumn);
        else if (sortOrder.equals("desc")) cond.orderByDesc(sortColumn);
        return baseMapper.selectPage(new Page<>(pageId, pageSize), cond);
    }
}
