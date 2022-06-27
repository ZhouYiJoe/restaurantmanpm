package com.example.demo;

import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.vo.OrderMenuVo;
import com.firstGroup.restaurant.model.vo.OrderVo;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class OrderControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxMjkzMTI5ODIzODI3MTgzMTI4IiwiZXhwIjoxNjUyNTkyOTYxLCJqdGkiOiIyMmVlZTI0MS1lODIyLTQxMGYtODE2Zi01Mjg2OGJlY2U0YmYifQ.w7vkgDH4-y7qJeuFONMpXuAZd_ZdxnK78Pp3YbKLDm8";


    // 第四次迭代

    /**
     * 测试分页查询订单，可指定排序方式、搜索关键词、日期等
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/order/findPage?pageId=1&pageSize=5&r_id=1238843327436247123&sortColumn=o_price&sortOrder=desc";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(Objects.requireNonNull(result.getBody()).getData());
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     *测试获取某个日期范围内的所有餐厅的总下单数量
     */
    @Test
    public void getNumWithinDateRange(){
        String url = "http://localhost:8080/order/getNumWithinDateRange/2022-03-19/2022-03-20";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(Objects.requireNonNull(result.getBody()).getData());
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }

    /**
     *测试获取一个餐厅在某个日期范围内总下单数量
     */
    @Test
    public void getNumInRestaurantWithinDateRange(){
        String url = "http://localhost:8080/order/getNumInRestaurantWithinDateRange/1238843327436247123/2022-03-19/2022-03-20";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        System.out.println(result);
        TestCase.assertNotNull(Objects.requireNonNull(result.getBody()).getData());
        TestCase.assertEquals("20000", result.getBody().getCode().toString());
    }


    // 第三次迭代

    /**
     *测试获取某个餐厅的订单总数
     */
    @Test
    public void getTotalInRestaurant(){
        Map<String, Object> request = new HashMap<>();
        request.put("restaurantId", "1238843327436247123");
        Result result = this.restTemplate.getForObject("http://localhost:8080/order/getTotalInRestaurant/{restaurantId}", Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试获取所有餐厅的订单总数
     */
    @Test
    public void getTotal(){
        Result result = this.restTemplate.getForObject("http://localhost:8080/order/getTotal", Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     *测试查询分页数据
     */
    @Test
    public void findListByPage(){
        Map<String, Object> request = new HashMap<>();
        request.put("rId", "1238843327436247123");
        request.put("current", 1);
        request.put("limit", 5);
        Result result = this.restTemplate.getForObject("http://localhost:8080/order/findListByPage/{rId}/{current}/{limit}",
                Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     * 测试根据id查询订单
     */
    @Test
    public void findById() {
        Result result = this.restTemplate.getForObject("http://localhost:8080/order/2022031923294916248", Result.class);
        TestCase.assertEquals("20000", result.getCode().toString());
        System.out.println(result);
    }

    // 第二次迭代测试

    /**
     * 测试获取用户所有订单
     */
    @Test
    public void findByCategory(){
        Map<String, Object> request = new HashMap<>();//请求参数
        request.put("customer_id", "1238832492932147234");
        Result result = this.restTemplate.getForObject("http://localhost:8080/order/findForCustomer/{customer_id}/", Result.class, request);//请求结果
        Map<String, Object> expected = new HashMap<>();
        expected.put("message","成功");
        TestCase.assertEquals(expected.get("message"), result.getMessage());
        System.out.println(result);
    }

    /**
     * 测试创建订单
     */
    @Test
    public void createOrderTest(){
        OrderMenuVo orderMenuVo = new OrderMenuVo(); //订单菜品
        orderMenuVo.setDId("0000000000000000000");
        orderMenuVo.setNumber(1);
        List<OrderMenuVo> list = new ArrayList<>();
        list.add(orderMenuVo);

        OrderVo orderVo = new OrderVo();
        orderVo.setAllPrice(new BigDecimal(Double.toString(24.8)));
        orderVo.setOrderMenuVoList(list);
        orderVo.setCId("1508373598497222657");
        orderVo.setDinnerNum(1);
        orderVo.setRId("1238843327436247123");
        orderVo.setNote("加辣！");

        Map<String, Object> request = new HashMap<>();//请求参数
        request.put("orderVo", orderVo);

        Result result = this.restTemplate.postForObject("http://localhost:8080/order/createOrder",orderVo, Result.class, orderVo);//请求结果
        Map<String, Object> expected = new HashMap<>();
        expected.put("message","成功");
        TestCase.assertEquals(expected.get("message"), result.getMessage());
        System.out.println(result);
    }

    /**
     * 测试获取用户在某个餐馆所有的订单
     */
    /*@Test
    public void findCustomerOrderByRestaurantId(){
        String url = "http://localhost:8080/order/findCustomerOrderByRestaurantId/{customer_id}/{r_id}/{current}/{limit}";
        Map<String, Object> request = new HashMap<>();//请求参数
        request.put("customer_id", "1238832492932147234");
        request.put("current", 1);
        request.put("limit", 5);
        request.put("r_id", "1238843327436247123");

        Result result = this.restTemplate.getForObject(url, Result.class, request);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData()); //返回数据是否为null
    }*/

    /**
     * 测试删除订单，且把此次订单修改的库存等数据全部恢复
     */
    @Test
    public void rollbackOrder(){
        //oId每次要改变，因为前一个已经删除
        String url = "http://localhost:8080/order/rollbackOrder?oId=2022040309332498738";
        this.restTemplate.delete(url);
    }

}
