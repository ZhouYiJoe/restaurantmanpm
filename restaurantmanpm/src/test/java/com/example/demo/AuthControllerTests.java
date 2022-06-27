package com.example.demo;

import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class AuthControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // 第五次迭代测试

    /**
     * 测试退出登录
     */
    @Test
    public void adminLogout(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODc1ODQ5LCJqdGkiOiI2OTNjMWE0YS0wYTI0LTRiMTItYWI3OC1hOTMzNjJjZGFkYzMifQ.2QIYgXHwgntM2AGSJrA-i27ui60UTkImv5fwhk_B3mc";
        String url = "http://localhost:8080/auth/adminLogout";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     *
     */
    @Test
    public void getToken(){

        String url = "http://localhost:8080/auth/token";

        // 有效的code
        Map request1 = new HashMap();
        request1.put("code", "1530718274239639553");
        Result result1 = this.restTemplate.postForObject(url, request1, Result.class);
        System.out.println(result1);
        TestCase.assertEquals("20000", result1.getCode().toString());

        // 无效的code
        Map request2 = new HashMap();
        request2.put("code", "20000");
        Result result2 = this.restTemplate.postForObject(url, request2, Result.class);
        System.out.println(result2);
        TestCase.assertEquals("20001", result2.getCode().toString());
    }

    /**
     * 验证token的有效性
     */
    @Test
    public void checkToken(){
        String url = "http://localhost:8080/auth/checkToken";
        // 有效的token
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODc1ODQ5LCJqdGkiOiI2OTNjMWE0YS0wYTI0LTRiMTItYWI3OC1hOTMzNjJjZGFkYzMifQ.2QIYgXHwgntM2AGSJrA-i27ui60UTkImv5fwhk_B3mc");
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getBody().getCode().toString());

        // 无效的token
        HttpHeaders headers2 = new HttpHeaders();
        headers.add("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDkyNTEzMDgsImp0aSI6ImI0ZWE3ODFjLTcyNWQtNDZmMi1iNTk3LWMyNmI5YzZiNTY4NSJ9.5k9bXyRnLsVrHciy4EJFCoPNzoBQAE9c9aGEhVKLygk");
        HttpEntity<String> request2 = new HttpEntity<>(null, headers2);
        ResponseEntity<Result> result2 = this.restTemplate.exchange(url, HttpMethod.GET, request2, Result.class);
        System.out.println(result2);
        TestCase.assertEquals("400 BAD_REQUEST", result2.getStatusCode().toString());
    }
}
