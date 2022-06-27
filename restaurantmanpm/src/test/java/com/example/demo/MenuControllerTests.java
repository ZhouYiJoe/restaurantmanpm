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
public class MenuControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODc3MjkyLCJqdGkiOiJkNjQ1YjRmOS02ZDQ0LTQ5MWYtOTViZC03MWFmNTRiNWE0YWUifQ.0_uPRnqOac4S_jZYJBG8L5wxBPgA2D70ocygrmbVE8Y";

    //第五次迭代

    /**
     * 测试新增
     */
    @Test
    public void add(){
        String url = "http://localhost:8080/menu";

        JSONObject body = new JSONObject();
        body.put("key", "ceshi");
        body.put("name", "测试");
        body.put("typeId", "1527635425604939778");

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
        String url = "http://localhost:8080/menu";

        JSONObject body = new JSONObject();
        body.put("key", "ceshi");
        body.put("name", "更新测试");
        body.put("typeId", "1527635425604939778");
        body.put("id", "1530748210996367362");

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
     * 测试分页查询
     */
    @Test
    public void page(){
        String url = "http://localhost:8080/menu/page?pageId=1&pageSize=5";

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
        String url = "http://localhost:8080/menu/1530748210996367362";

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
     * 测试查询角色拥有的所有权限
     */
    @Test
    public void findForRole(){
        String url = "http://localhost:8080/menu/findForRole/0000000000000000001";

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
