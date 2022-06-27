package com.example.demo;

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
public class FeedbackControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjU1MjE4MDI3LCJqdGkiOiJiNDgyZjVkNy1lY2MxLTRiZTktYmVmYS05OWNmY2VmYzkzMWQifQ.0gZEYKV3iTHDQkZNKEkf8WEQ1R0ZfReJl9N8pp3MPbA";

    // 第六次迭代

    /**
     * 测试建议id查询
     */
    @Test
    public void findByAdviceId(){
        String url = "http://localhost:8080/feedback/findByAdviceId/1513526197146476545";

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


    //第五次迭代

    /**
     * 测试新增反馈
     */
    @Test
    public void add(){
        String url = "http://localhost:8080/feedback";

        JSONObject body = new JSONObject();
        body.put("adviceId", "0000000000000000002");
        body.put("aid", "1530718274239639553");
        body.put("content", "测试反馈");

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
     * 测试更新反馈
     */
    @Test
    public void update(){
        String url = "http://localhost:8080/feedback";

        JSONObject body = new JSONObject();
        body.put("adviceId", "0000000000000000001");
        body.put("aid", "1530718274239639553");
        body.put("content", "更新反馈更新反馈更新反馈");
        body.put("fid", "1530737573910065154");

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
     * 测试分页查询反馈
     */
    @Test
    public void findListByPage(){
        String url = "http://localhost:8080/feedback?page=1&pageCount=5";

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
        String url = "http://localhost:8080/feedback/1530737573910065154";

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Feedback> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Feedback.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
        TestCase.assertEquals("1530737573910065154", result.getBody().getFId());
    }

    /**
     * 测试删除反馈
     */
    @Test
    public void delete(){
        String url = "http://localhost:8080/feedback/1530742018324606977";

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
