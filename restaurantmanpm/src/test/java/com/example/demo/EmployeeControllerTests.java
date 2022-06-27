package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import junit.framework.TestCase;
import org.json.JSONException;
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
public class EmployeeControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;


    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjU1MjE4MDI3LCJqdGkiOiJiNDgyZjVkNy1lY2MxLTRiZTktYmVmYS05OWNmY2VmYzkzMWQifQ.0gZEYKV3iTHDQkZNKEkf8WEQ1R0ZfReJl9N8pp3MPbA";

    // 第六次迭代

    /**
     * 测试新增
     */
    @Test
    public void add(){
        String url = "http://localhost:8080/employee";

        JSONObject body = new JSONObject();
        body.put("ejob", "cook");
        body.put("ename", "chenanyi");
        body.put("esex", 0);
        body.put("esalary", 99999);
        body.put("rid", "1238843327436247123");

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
        String url = "http://localhost:8080/employee";

        JSONObject body = new JSONObject();
        body.put("eid", "1517330742546280449");
        body.put("esalary", 99999);
        body.put("rid", "1238843327436247123");

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

    // 第三次迭代

    /**
     * 测试获取所有餐厅的员工总数
     */
    @Test
    public void getTotalNum(){
        String url = "http://localhost:8080/employee/getTotalNum";
        String result = this.restTemplate.getForObject(url, String.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
    }

    /**
     * 测试获取所有餐厅各个职位的员工的数量
     */
    @Test
    public void getNumForEachPos(){
        String url = "http://localhost:8080/employee/getNumForEachPos";
        String result = this.restTemplate.getForObject(url, String.class);
        System.out.println(result);
        TestCase.assertNotNull(result);
    }

    /**
     * 测试获取一个餐厅各个职位的员工的数量
     */
    @Test
    public void getNumForEachPosInRestaurant(){
        String url = "http://localhost:8080/employee/getNumForEachPosInRestaurant/{restaurantId}";
        Map request = new HashMap();
        request.put("restaurantId", "1238843327436247123");
        String result = this.restTemplate.getForObject(url, String.class, request);
        System.out.println(result);
        TestCase.assertNotNull(result);
    }

    /**
     * 测试获取某个餐厅的员工总数
     */
    @Test
    public void getNumInRestaurant(){
        String url = "http://localhost:8080/employee/getNumInRestaurant/{r_id}";
        Map request = new HashMap();
        request.put("r_id", "1238843327436247123");
        String result = this.restTemplate.getForObject(url, String.class, request);
        System.out.println(result);
        TestCase.assertNotNull(result);
    }

    /**
     * 测试查询分页数据
     */
    @Test
    public void findListByPage(){
        String url = "http://localhost:8080/employee/findListByPage/{rId}/{current}/{limit}";
        Map request = new HashMap();
        request.put("rId", "1238843327436247123");
        request.put("current", 1);
        request.put("limit", 5);
        String result = this.restTemplate.getForObject(url, String.class, request);
        System.out.println(result);
        TestCase.assertNotNull(result);
    }
}
