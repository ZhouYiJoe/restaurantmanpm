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
public class RestaurantControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // 第三次迭代

    /**
     *测试获取不同地区的餐厅分布数量
     */
    @Test
    public void getNumInEachArea(){
        Result result = this.restTemplate.getForObject("http://localhost:8080/restaurant/getNumInEachArea", Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试获取餐厅的总数量
     */
    @Test
    public void getTotalNum(){
        Result result = this.restTemplate.getForObject("http://localhost:8080/restaurant/getTotalNum", Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    // 第二次迭代接口

    /**
     * 测试根据id查询
     */
    @Test
    public void findById(){

        String url = "http://localhost:8080/restaurant/{id}";
        Map request = new HashMap();
        request.put("id", "1238843327436247123");
        Result result = this.restTemplate.getForObject(url, Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }

    /**
     * 测试查询所有餐馆数据
     */
    @Test
    public void findListByPage(){

        String url = "http://localhost:8080/restaurant/findAll";
        Result result = this.restTemplate.getForObject(url, Result.class);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }
}
