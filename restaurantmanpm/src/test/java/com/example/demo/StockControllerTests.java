package com.example.demo;

import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class StockControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODkxMzQwLCJqdGkiOiJjOWQ5YzBlNi1jOTZlLTQxZGItYjViZS05ODAzYTgyMWZiMzkifQ.WzcxgR46M5nUPt9NLR07U8uVIUCAVEjNnvlG-XpIha4";

    //第五次迭代

    /**
     * 测试获取所有阈值之下的物资数据
     */
    @Test
    public void findAllBelowThreshold(){
        String url = "http://localhost:8080/stock/findAllBelowThreshold?r_id=1238843327436247123";

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }


    // 第四次迭代

    /**
     * 测试分页查询库存，可指定排序方式等
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/stock/findPage?pageId=1&pageSize=5&r_id=1238843327436247123&cateId=0000000000000000000&sortColumn=s_stock&sortOrder=asc&keyword=菜";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    //第三次迭代

    /**
     *测试获取一个餐厅的所有库存信息
     */
    @Test
    public void getAllInRestaurant(){
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        Result result = this.restTemplate.getForObject("http://localhost:8080/stock/getAllInRestaurant/{restaurantId}", Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    // 第二次迭代测试

    /**
     * 测试查询分页数据
     */
    @Test
    public void findListByPage(){

        String url = "http://localhost:8080/stock/findListByPage/{current}/{limit}";
        Map request = new HashMap();
        request.put("current", 1);
        request.put("limit", 5);
        Result result = this.restTemplate.getForObject(url, Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }
}
