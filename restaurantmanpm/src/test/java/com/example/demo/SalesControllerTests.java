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
public class SalesControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    //第三次迭代

    /**
     *测试计算一个餐厅在某个时间段内的销售额
     */
    @Test
    public void getNumInRestaurantWithinDateRange(){
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        request.put("from", "2022-03-16");
        request.put("to", "2022-04-17");
        Result result = this.restTemplate.getForObject("http://localhost:8080/sales/getInRestaurantWithinDateRange/{restaurantId}/{from}/{to}",
                Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试计算一个餐厅的总销售额
     */
    @Test
    public void getTotalInRestaurant(){
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        Result result = this.restTemplate.getForObject("http://localhost:8080/sales/getTotalInRestaurant/{restaurantId}", Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试计算所有餐厅在某个时间段内的总销售额
     */
    @Test
    public void getWithinDateRange(){
        Map<String, Object> request = new HashMap<>();
        request.put("from", "2022-03-16");
        request.put("to", "2022-04-17");
        Result result = this.restTemplate.getForObject("http://localhost:8080/sales/getWithinDateRange/{from}/{to}",
                Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试计算所有餐厅的总销售额
     */
    @Test
    public void getTotal(){
        Result result = this.restTemplate.getForObject("http://localhost:8080/sales/getTotal", Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }
}
