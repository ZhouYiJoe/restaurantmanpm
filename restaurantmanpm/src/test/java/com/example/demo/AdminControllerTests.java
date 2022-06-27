package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Admin;
import com.firstGroup.restaurant.model.vo.Authentication;
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
public class AdminControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjU1MjE4MDI3LCJqdGkiOiJiNDgyZjVkNy1lY2MxLTRiZTktYmVmYS05OWNmY2VmYzkzMWQifQ.0gZEYKV3iTHDQkZNKEkf8WEQ1R0ZfReJl9N8pp3MPbA";

    // 第六次迭代

    /**
     * 测试新增
     */
    @Test
    public void add(){
        String url = "http://localhost:8080/admin";

        JSONObject admin = new JSONObject();
        admin.put("aaccount", "chenanyi");
        admin.put("rid", "1238843327436247123");
        admin.put("apassword", "123456");
        admin.put("aname", "陈安宜");
        admin.put("apermission", 0);

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(admin.toString(), headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.POST, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }


    // 第五次迭代

    /**
     * 测试分页查询
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/admin/page?pageId=1&pageSize=5";

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


    // 第三次迭代测试

    /**
     * 测试后台登录
     */
    @Test
    public void login(){
        String url = "http://localhost:8080/admin/login";
        Authentication request = new Authentication();
        request.setUsername("admin");
        request.setPassword("123456");
        Result result = this.restTemplate.postForObject(url, request, Result.class);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }

    /**
     * 测试根据token返回信息
     */
    @Test
    public void info(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxMjkzMTI5ODIzODI3MTgzMTI4IiwiZXhwIjoxNjUwODk5NDc1LCJqdGkiOiJmN2JhMThhMS05ZGRlLTRlYWEtYTA3Ny0zNjFjZTIxNjczZjcifQ.PSGPirPSCwJr48m6cCSct18nhD3-ryV0g0igxA_O7lM";
        String url = "http://localhost:8080/admin/info?token=" + token;
        Map<String, Object> request = new HashMap<>();
        request.put("token", token);
        Result result = this.restTemplate.getForObject(url, Result.class);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }
}
