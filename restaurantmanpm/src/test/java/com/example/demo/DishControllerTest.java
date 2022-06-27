package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Dish;
import com.firstGroup.restaurant.model.vo.DishMcVo;
import com.firstGroup.restaurant.model.vo.DishVo;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class DishControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjUzODc3MjkyLCJqdGkiOiJkNjQ1YjRmOS02ZDQ0LTQ5MWYtOTViZC03MWFmNTRiNWE0YWUifQ.0_uPRnqOac4S_jZYJBG8L5wxBPgA2D70ocygrmbVE8Y";

    //第五次迭代

    /**
     * 测试增加销量
     */
    @Test
    public void increaseSales(){
        String url = "http://localhost:8080/dish/increaseSales/0000000000000000000";

        JSONObject body = new JSONObject();
        body.put("increment", 65);

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


    // 第四次迭代

    /**
     * 测试新增菜品
     */
    @Test
    public void add() {

        DishVo dishVo = new DishVo();
        dishVo.setCateId("0000000000000000005");
        dishVo.setDDescribe("测试测试");
        dishVo.setDImgurl("string");
        List<DishMcVo> dishMcVos = new ArrayList<>();
        DishMcVo dishMcVo = new DishMcVo();
        dishMcVo.setNumber(new BigDecimal("5"));
        dishMcVo.setMcId("0000000000000000000");
        dishMcVos.add(dishMcVo);
        dishVo.setDishMcVos(dishMcVos);
        dishVo.setDName("测试");
        dishVo.setDPrice(new BigDecimal("0"));
        dishVo.setRId("1238843327436247123");
        String body = JSON.toJSONString(dishVo);

        String url = "http://localhost:8080/dish";
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


    // 第三次迭代

    /**
     * 测试获取一个餐厅销量排行前N的菜品
     */
    @Test
    public void getSalesTopN(){
        String url = "http://localhost:8080/dish/getSalesTopN/{restaurantId}/{n}";
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        request.put("n", 5);
        Result result = this.restTemplate.getForObject(url, Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }

    /**
     * 测试分页查询菜品，可指定餐厅、菜品分类、排序方式、模糊查询关键字
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/dish/findPage?";
        String request = "pageId=1&pageSize=5&restaurantId=1238843327436247123&categoryId=0000000000000000007&sortColumn=d_id&sortOrder=asc";
        Result result = this.restTemplate.getForObject(url+request, Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }

    /**
     * 测试更新菜品
     */
    @Test
    public void update() {
        Dish dish = new Dish();
        dish.setRId("1238843327436247123");
        dish.setDId("1518417940184166402");
        dish.setDName("测试更新");
        this.restTemplate.put("http://localhost:8080/dish", dish, Result.class);
    }

    /**
     * 测试根据id查询菜品
     */
    @Test
    public void findById() {
        Result result = this.restTemplate.getForObject("http://localhost:8080/dish/1518417940184166402", Result.class);
        TestCase.assertEquals("20000", result.getCode().toString());
        System.out.println(result);
    }

    /**
     * 测试根据id删除菜品
     */
    @Test
    public void delete() {
        this.restTemplate.delete("http://localhost:8080/dish/1518417940184166402");
    }


    // 第一次迭代测试

    /**
     * 测试分页显示某种分类下的菜品
     */
    @Test
    public void findByCategory(){
        Map<String, Object> request = new HashMap<>();//请求参数
        request.put("category_id", "0000000000000000005");
        request.put("current", 1);
        request.put("limit", 5);

        Result result = this.restTemplate.getForObject("http://localhost:8080/dish/findByCategory/{category_id}/{current}/{limit}",
                Result.class, request);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     * 分页查询所有菜品数据
     */
    @Test
    public void findListByPage(){

        Result result = this.restTemplate.getForObject("http://localhost:8080/dish?current=1&limit=5",
                Result.class, (Object) null);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result);
    }

//    @Test
//    public void findPreferDishTest(){ //测试某种分类下所有菜品
//        Map<String, Object> request = new HashMap<>();//请求参数
//        request.put("limit", 5);
//
//        Result result = this.restTemplate.getForObject("http://localhost:8080/dish/findPreferDish/{limit}",
//                Result.class,
//                request);
//        System.out.println(result.getData());
//    }
}
