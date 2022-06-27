package com.example.demo;

import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class FoodtableControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // 第三次迭代测试

    /**
     *测试获取处于不同状态的餐桌数量，状态0-未使用，1-使用中
     */
    @Test
    public void getNumForEachState(){
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        Result result = this.restTemplate.getForObject("http://localhost:8080/foodtable/getNumForEachState/{restaurantId}",
                Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试获取某个餐厅的餐桌总数
     */
    @Test
    public void getNumInRestaurant(){
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        Result result = this.restTemplate.getForObject("http://localhost:8080/foodtable/getNumInRestaurant/{restaurantId}",
                Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试查询分页数据
     */
    @Test
    public void findListByPage(){
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        request.put("current", 1);
        request.put("limit", 5);
        Result result = this.restTemplate.getForObject("http://localhost:8080/foodtable/findListByPage/{restaurantId}/{current}/{limit}",
                Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }
}
