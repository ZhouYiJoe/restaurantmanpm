package com;

import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

/**
 * @ClassName Test1
 * @Description TODO
 * @Author Haojie
 * @Date 2022/5/25 15:45
 */
public class RecommendTest {


    public static void main(String[] args) throws Exception {

        RecommendTest.userCFRecommend();

    }

    public static void userCFRecommend() throws Exception {
        String filePath = "C:\\Users\\jie\\Desktop\\test2.txt";
        // 建立数据模型，不包含用户评分
        DataModel dm = new GenericBooleanPrefDataModel(
                GenericBooleanPrefDataModel.
                        toDataMap(new FileDataModel(new File(filePath))));


        // 使用曼哈顿距离计算类似度
        UserSimilarity us = new CityBlockSimilarity(dm);

        //指定NearestNUserNeighborhood做为近邻算法
        UserNeighborhood unb = new NearestNUserNeighborhood(10, us, dm);

        // 构建不包含用户评分的UserCF推荐器
        Recommender re = new GenericBooleanPrefUserBasedRecommender(dm, unb, us);

        // 输出推荐结果，为1号用户推荐5个商品
        List<RecommendedItem> list = re.recommend(1, 2);
        for (RecommendedItem recommendedItem : list) {
            System.out.println(recommendedItem.getItemID() + " : " + recommendedItem.getValue());
        }
    }



}
