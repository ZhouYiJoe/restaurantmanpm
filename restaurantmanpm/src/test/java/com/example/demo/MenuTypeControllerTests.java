package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Feedback;
import com.firstGroup.restaurant.model.MenuType;
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
public class MenuTypeControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODg3MzQ0LCJqdGkiOiJmYTY4ZmQyNy05Yjg5LTQ4YjAtODExNy1hMWRlMDBlYzJiMGIifQ.cF5x6P47MEpRGmBRtToLNkz-hMvOChq-NwL_5raKkV8";

    //第五次迭代

    /**
     * 测试新增
     */
    @Test
    public void add(){
        String url = "http://localhost:8080/menuType";

        JSONObject body = new JSONObject();
        body.put("name", "测试管理");
        body.put("key", "测试测试测试");

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
     * 测试更新
     */
    @Test
    public void update(){
        String url = "http://localhost:8080/menuType";

        JSONObject body = new JSONObject();
        body.put("name", "测试管理");
        body.put("key", "更新测试测试测试");
        body.put("id", "1530780179390013442");

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.PUT, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试根据id查询
     */
    @Test
    public void selectById(){
        String url = "http://localhost:8080/menuType/1530780179390013442";

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

    /**
     * 测试删除
     */
    @Test
    public void delete(){
        String url = "http://localhost:8080/menuType/1530780179390013442";

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

    /**
     * 测试获取所有权限类别信息
     */
    @Test
    public void findAll(){
        String url = "http://localhost:8080/menuType";

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
}
