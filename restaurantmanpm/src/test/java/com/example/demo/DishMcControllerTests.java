package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.DishMc;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class DishMcControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxMjkzMTI5ODIzODI3MTgzMTI4IiwiZXhwIjoxNjUyNTkyOTYxLCJqdGkiOiIyMmVlZTI0MS1lODIyLTQxMGYtODE2Zi01Mjg2OGJlY2U0YmYifQ.w7vkgDH4-y7qJeuFONMpXuAZd_ZdxnK78Pp3YbKLDm8";

    // 第四次迭代

    @Test
    public void findAllForDish() {

        String url = "http://localhost:8080/dish-mc/findAllForDish/1238843327436247123";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    // 第一次迭代测试

    /**
     * 测试获取一个菜品所含有的材料
     */
    @Test
    public void findForDish() throws JSONException {

        String url = "http://localhost:8080/dish-mc?d_id=0000000000000000000";
        String result = this.restTemplate.getForObject(url, String.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
    }

    /**
     * 测试查询单个
     */
    @Test
    public void findById() throws JSONException {

        String url = "http://localhost:8080/dish-mc/{mcId}/{dId}";
        Map request = new HashMap();
        request.put("mcId", "0000000000000000003");
        request.put("dId", "0000000000000000000");
        String result = this.restTemplate.getForObject(url, String.class, request);
        System.out.println(result);
        TestCase.assertNotNull(result);
    }
}
