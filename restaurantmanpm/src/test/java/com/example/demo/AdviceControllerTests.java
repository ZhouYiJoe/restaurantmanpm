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

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class AdviceControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjU1MjE4MDI3LCJqdGkiOiJiNDgyZjVkNy1lY2MxLTRiZTktYmVmYS05OWNmY2VmYzkzMWQifQ.0gZEYKV3iTHDQkZNKEkf8WEQ1R0ZfReJl9N8pp3MPbA";

    // 第六次迭代

    /**
     * 测试分页查询建议，可指定排序方式等
     */
    @Test
    public void findPageByCustomerId(){
        String url = "http://localhost:8080/advice/findPageByCustomerId?pageId=1&pageSize=5&r_id=1238843327436247123&customerId=1238832492932147234";

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
     * 测试分页查询建议
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/advice/findPage?pageId=1&pageSize=5&r_id=1238843327436247123&sortColumn=star&sortOrder=asc&status=0&from=2022-4-01&to=2022-4-10";

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
     * 测试分页查询大于等于指定星级的数量
     */
    @Test
    public void findNumberByDate(){
        String url = "http://localhost:8080/advice/findNumberByDate?r_id=1238843327436247123&star=4&status=0&from=2022-4-01&to=2022-4-10";

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
     * 测试修改建议数量
     */
    @Test
    public void updateStatus(){
        String url = "http://localhost:8080/advice/updateStatus/0000000000000000000/1";

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.PUT, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    // 第二次迭代测试

    /**
     * 测试提交建议
     */
    @Test
    public void advice(){

        String url = "http://localhost:8080/advice";
        Map request = new HashMap();
        request.put("cid", "1508373598497222657");
        request.put("content", "这是一条建议");
        request.put("name", "菜品问题");
        request.put("oid", "2022031923294916248");
        request.put("star", 5);
        Result result = this.restTemplate.postForObject(url, request, Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }
}
