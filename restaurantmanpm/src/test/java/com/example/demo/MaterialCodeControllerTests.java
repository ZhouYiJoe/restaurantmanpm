package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.MaterialCode;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class MaterialCodeControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxMjkzMTI5ODIzODI3MTgzMTI4IiwiZXhwIjoxNjUyNTkyOTYxLCJqdGkiOiIyMmVlZTI0MS1lODIyLTQxMGYtODE2Zi01Mjg2OGJlY2U0YmYifQ.w7vkgDH4-y7qJeuFONMpXuAZd_ZdxnK78Pp3YbKLDm8";


    // 第四次迭代测试

    /**
     * 测试删除
     */
    @Test
    public void delete(){
        String url = "http://localhost:8080/material-code/1525374824942264321";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.DELETE, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试id查询
     */
    @Test
    public void findById(){
        String url = "http://localhost:8080/material-code/1525374824942264321";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试更新
     */
    @Test
    public void update(){

        MaterialCode materialCode = new MaterialCode();
        materialCode.setMcId("1525374824942264321");
        materialCode.setCateId("0000000000000000000");
        materialCode.setMcDescription("更新测试测试");
        materialCode.setMcImgurl("https://img.szu.com/12");
        materialCode.setMcName("更新");
        materialCode.setMcPriceperunit(new BigDecimal("3"));
        materialCode.setMcThreshold(new BigDecimal("3"));
        materialCode.setMcUnit("kg");
        String body = JSON.toJSONString(materialCode);

        String url = "http://localhost:8080/material-code";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.PUT, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试新增
     */
    @Test
    public void add(){

        MaterialCode materialCode = new MaterialCode();
        materialCode.setCateId("0000000000000000000");
        materialCode.setMcDescription("测试测试");
        materialCode.setMcImgurl("https://img.szu.com/12");
        materialCode.setMcName("测试");
        materialCode.setMcPriceperunit(new BigDecimal("3"));
        materialCode.setMcThreshold(new BigDecimal("3"));
        materialCode.setMcUnit("kg");
        String body = JSON.toJSONString(materialCode);

        String url = "http://localhost:8080/material-code";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.POST, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试获取所有的配料
     */
    @Test
    public void findAll(){
        String url = "http://localhost:8080/material-code/findAll";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试分页查询
     */
    @Test
    public void findListByPage(){
        String url = "http://localhost:8080/material-code/findListByPage/1/5";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }
}
