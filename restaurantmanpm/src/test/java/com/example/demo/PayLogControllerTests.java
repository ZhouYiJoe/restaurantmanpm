package com.example.demo;

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
public class PayLogControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNTMwNzE4Mjc0MjM5NjM5NTUzIiwiZXhwIjoxNjU1MjE4MDI3LCJqdGkiOiJiNDgyZjVkNy1lY2MxLTRiZTktYmVmYS05OWNmY2VmYzkzMWQifQ.0gZEYKV3iTHDQkZNKEkf8WEQ1R0ZfReJl9N8pp3MPbA";

    // 第六次迭代

    /**
     * 测试根据订单号请求微信支付二维码
     */
    @Test
    public void createNative(){
        String url = "http://localhost:8080/paylog/createNative/2022061323411703779/测试备注";

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

    // 第二次迭代测试


    /**
     * 测试根据订单号查询订单支付状态，并更改订单支付状态
     */
    @Test
    public void queryPayStatus(){

        String url = "http://localhost:8080/paylog/queryPayStatus/{orderNo}";
        //下面是支付成功的情况
        Map request1 = new HashMap();
        request1.put("orderNo", "2022031923294916248");
        Result result1 = this.restTemplate.getForObject(url, Result.class, request1);//请求结果
        System.out.println(result1);
        TestCase.assertEquals("20000", result1.getCode().toString());

        //下面是支付中情况
        Map request2 = new HashMap();
        request2.put("orderNo", "2022041122094270463");
        Result result2 = this.restTemplate.getForObject(url, Result.class, request2);//请求结果
        System.out.println(result2);
        TestCase.assertEquals("25000", result2.getCode().toString());
    }
}
