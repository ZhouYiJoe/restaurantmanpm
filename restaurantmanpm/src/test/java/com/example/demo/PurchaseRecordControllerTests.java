package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Feedback;
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
public class PurchaseRecordControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODkxMzQwLCJqdGkiOiJjOWQ5YzBlNi1jOTZlLTQxZGItYjViZS05ODAzYTgyMWZiMzkifQ.WzcxgR46M5nUPt9NLR07U8uVIUCAVEjNnvlG-XpIha4";

    //第五次迭代

    /**
     * 测试分页查询采购记录，可指定排序方式等
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/purchase-record/findPage?pageId=1&pageSize=5&r_id=1238843327436247123&sortColumn=expected_total_price&sortOrder=desc";

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
     * 测试完成指定采购记录
     */
    @Test
    public void updateStatusByPurchaseRecordId(){
        String url = "http://localhost:8080/purchase-record/updateStatusByPurchaseRecordId?purchaseRecordId=1528011149871157250&aId=1530718274239639553&note=测试完成采购记录";

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.POST, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试创建采购记录
     */
    @Test
    public void createPurchaseRecord(){
        String url = "http://localhost:8080/purchase-record/createPurchaseRecord";

        JSONObject body = new JSONObject();
        body.put("aid", "1530718274239639553");
        body.put("rid", "1238843327436247123");

        JSONArray purVos = new JSONArray();
        JSONObject purVo = new JSONObject();
        purVo.put("bid", "1528010760773963778");
        purVo.put("expectedNumber", 999);
        purVo.put("expectedPrice", 9999);
        purVo.put("mcId", "0000000000000000001");
        purVos.add(purVo);

        body.put("purchaseListVos", purVos);

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
        String url = "http://localhost:8080/purchase-record";

        JSONObject body = new JSONObject();
        body.put("aid", "1530718274239639553");
        body.put("rid", "1238843327436247123");
        body.put("expectedTotalPrice", 9999);
        body.put("note", "测试采购");
        body.put("status", 0);
        body.put("id", "1530801558458060801");

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
    public void findListByPage(){
        String url = "http://localhost:8080/purchase-record/findListByPage/1/5";

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
     * 测试根据id查询
     */
    @Test
    public void findById(){
        String url = "http://localhost:8080/purchase-record/1530801558458060801";

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
        String url = "http://localhost:8080/purchase-record/1530801558458060801";

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
