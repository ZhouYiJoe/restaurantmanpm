package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.firstGroup.restaurant.mapper.CustomerMapper;
import com.firstGroup.restaurant.mapper.DishMapper;
import com.firstGroup.restaurant.mapper.OrderMapper;
import com.firstGroup.restaurant.model.Dish;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RecommendDishService
 * @Description TODO
 * @Author Haojie
 * @Date 2022/5/29 20:28
 */
@Service
public class RecommendDishService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private DishMapper dishMapper;

    private String filename1 = "userItemsWithoutScore.txt";
    private String filename2 = "userItemsWithScore.txt";

    /**
     * @Postcontruct 在依赖注入完成后自动调用
     * @Description 生成用户id-菜品id文件
     */
//    @PostConstruct
//    public void getUserItems() {
//        try (Writer w1 = new BufferedWriter(new FileWriter(filename1));
//             Writer w2 = new BufferedWriter(new FileWriter(filename2))) {
//            // 查询所有用户
//            List<Customer> customers = customerMapper.selectList(null);
//            // 查询所有用户的下单菜品记录
//            for (Customer customer : customers) {
//                List<DishNumber> dishNumbers = orderMapper.getUserOrderDishes(customer.getCId());
//                if (dishNumbers.size() == 0)
//                    continue;
//
//                for (DishNumber d : dishNumbers) {
//                    w1.write(customer.getId() + "," + d.getDId() + "\n");
//                    w2.write(customer.getId() + "," + d.getDId() + "," + d.getValue() + "\n");
//                }
//            }
//            w1.flush();
//            w2.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // 对外暴露的接口
    public List<Dish> userCFRecommend(String cId, Integer number, Boolean withScore) throws Exception {
        List<Dish> dishes;
        if (withScore) {
            dishes = this.userCFWithoutRecommend(cId, number);
        } else {
            dishes = this.userCFWithoutScoreRecommend(cId, number);
        }
        return dishes;
    }


    /*
     * @Author Haojie
     * @Description 有评分的基于用户的协同过滤推荐算法
     * @Param
     * @return
     **/
    private List<Dish> userCFWithoutRecommend(String cId, Integer number) throws Exception {

        // 建立数据模型，包含用户评分
        DataModel dm = new GenericDataModel(
                GenericDataModel
                        .toDataMap(new FileDataModel(new File(filename2))));

        // 使用曼哈顿距离计算类似度
        UserSimilarity us = new EuclideanDistanceSimilarity(dm);

        //指定NearestNUserNeighborhood做为近邻算法
        UserNeighborhood unb = new NearestNUserNeighborhood(10, us, dm);

        // 构建包含用户评分的UserCF推荐器
        Recommender re = new GenericUserBasedRecommender(dm, unb, us);

        // 返回推荐结果，为cId用户推荐number个商品
        Integer id = customerMapper.selectById(cId).getId();
        List<RecommendedItem> list = re.recommend(id, number);
        List<Dish> dishes = new ArrayList<>();
        System.out.println("根据用户cId=" + cId + ", userId=" + id + "的点餐习惯，推荐的前" + number + "个菜品的id和推荐度如下：");

        for (RecommendedItem recommendedItem : list) {
            System.out.println(recommendedItem.getItemID() + " : " + recommendedItem.getValue());
            dishes.add(dishMapper.selectOne(new LambdaQueryWrapper<Dish>().eq(Dish::getId,
                    recommendedItem.getItemID())));
        }
        return dishes;
    }


    /*
     * @Author Haojie
     * @Description 无评分的基于用户的协同过滤推荐算法
     * @Param
     * @return
     **/
    private List<Dish> userCFWithoutScoreRecommend(String cId, Integer number) throws Exception {

        // 建立数据模型，不包含用户评分
        DataModel dm = new GenericDataModel(
                GenericDataModel
                        .toDataMap(new FileDataModel(new File(filename1))));

        // 使用曼哈顿距离计算类似度
        UserSimilarity us = new CityBlockSimilarity(dm);

        //指定NearestNUserNeighborhood做为近邻算法
        UserNeighborhood unb = new NearestNUserNeighborhood(5, us, dm);

        // 构建不包含用户评分的UserCF推荐器
        Recommender re = new GenericBooleanPrefUserBasedRecommender(dm, unb, us);

        // 返回推荐结果，为cId用户推荐number个商品
        Integer id = customerMapper.selectById(cId).getId();
        List<RecommendedItem> list = re.recommend(id, number);
        List<Dish> dishes = new ArrayList<>();
        System.out.println("根据用户cId=" + cId + ", userId=" + id + "的点餐习惯，推荐的前" + number + "个菜品的id和推荐度如下：");

        for (RecommendedItem recommendedItem : list) {
            System.out.println(recommendedItem.getItemID() + " : " + recommendedItem.getValue());
            dishes.add(dishMapper.selectOne(new LambdaQueryWrapper<Dish>().eq(Dish::getId,
                    recommendedItem.getItemID())));
        }
        return dishes;
    }


}

