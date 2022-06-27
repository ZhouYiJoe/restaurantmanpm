package com.example.demo;

import com.alibaba.fastjson.JSONArray;
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

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class PurchaseListControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjU1MjE4MDI3LCJqdGkiOiJiNDgyZjVkNy1lY2MxLTRiZTktYmVmYS05OWNmY2VmYzkzMWQifQ.0gZEYKV3iTHDQkZNKEkf8WEQ1R0ZfReJl9N8pp3MPbA";

    // 第六次迭代

    /**
     * 测试修改指定采购清单的价格和数量
     */
    @Test
    public void updatePriceAndNumber(){
        String url = "http://localhost:8080/purchase-list/updatePriceAndNumber";

        JSONObject body = new JSONObject();
        body.put("actualNumber", 500);
        body.put("actualPrice", 500);
        body.put("id", "1528028769446006786");
        JSONArray bodys = new JSONArray();
        bodys.add(body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(bodys.toString(), headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.POST, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     * 测试分页查询反馈
     */
    @Test
    public void findByPurchaseRecordId(){
        String url = "http://localhost:8080/purchase-list/findByPurchaseRecordId?purchaseRecordId=1528011149871157250";

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

    // 第五次迭代

    /**
     * 测试分页查询采购清单，可指定排序方式等
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/purchase-list/findPage?pageId=1&pageSize=5&sortColumn=modify_time&sortOrder=asc";

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


    // 第一次迭代测试

    /**
     * 测试查询分页数据
     */
    @Test
    public void findListByPage(){

        String url = "http://localhost:8080/purchase-list/findListByPage/{current}/{limit}";
        Map request = new HashMap();
        request.put("current", 1);
        request.put("limit", 5);
        Result result = this.restTemplate.getForObject(url, Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }
}
