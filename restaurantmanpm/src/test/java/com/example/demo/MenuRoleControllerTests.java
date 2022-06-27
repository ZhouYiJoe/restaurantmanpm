package com.example.demo;

import com.alibaba.fastjson.JSONObject;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class MenuRoleControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODkxMzQwLCJqdGkiOiJjOWQ5YzBlNi1jOTZlLTQxZGItYjViZS05ODAzYTgyMWZiMzkifQ.WzcxgR46M5nUPt9NLR07U8uVIUCAVEjNnvlG-XpIha4";

    //第五次迭代

    /**
     * 测试新增
     */
    @Test
    public void add(){
        String url = "http://localhost:8080/menuRole";

        JSONObject body = new JSONObject();
        body.put("menuId", "1530768856593735681");
        body.put("roleId", "0000000000000000001");

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.POST, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试删除
     */
    @Test
    public void delete(){
        String url = "http://localhost:8080/menuRole/1530768856593735681/0000000000000000001";

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.DELETE, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }
}
