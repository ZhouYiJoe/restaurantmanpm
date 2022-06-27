package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Feedback;
import com.firstGroup.restaurant.model.McBuyer;
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
public class McBuyerControllerTests {


    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODc3MjkyLCJqdGkiOiJkNjQ1YjRmOS02ZDQ0LTQ5MWYtOTViZC03MWFmNTRiNWE0YWUifQ.0_uPRnqOac4S_jZYJBG8L5wxBPgA2D70ocygrmbVE8Y";


    //第五次迭代

    /**
     * 测试新增
     */
    @Test
    public void add(){
        String url = "http://localhost:8080/mc-buyer";

        JSONObject body = new JSONObject();
        body.put("bid", "1528010760773963778");
        body.put("mcId", "0000000000000000001");

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<Integer> result = this.restTemplate.exchange(url, HttpMethod.POST, request, Integer.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("200 OK", result.getStatusCode().toString());
    }

    /**
     * 测试更新
     */
    @Test
    public void update(){
        String url = "http://localhost:8080/mc-buyer";

        JSONObject body = new JSONObject();
        body.put("bid", "1528010760773963778");
        body.put("id", "1530744743472967682");
        body.put("mcId", "0000000000000000022");

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<Integer> result = this.restTemplate.exchange(url, HttpMethod.PUT, request, Integer.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("200 OK", result.getStatusCode().toString());
    }

    /**
     * 测试分页查询
     */
    @Test
    public void findListByPage(){
        String url = "http://localhost:8080/mc-buyer?page=1&pageCount=5";

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
        String url = "http://localhost:8080/mc-buyer/1530744743472967682";

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<McBuyer> result = this.restTemplate.exchange(url, HttpMethod.GET, request, McBuyer.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("1530744743472967682", result.getBody().getId());
    }

    /**
     * 测试删除反馈
     */
    @Test
    public void delete(){
        String url = "http://localhost:8080/mc-buyer/1530744743472967682";

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Integer> result = this.restTemplate.exchange(url, HttpMethod.DELETE, request, Integer.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("200 OK", result.getStatusCode().toString());
    }
}
