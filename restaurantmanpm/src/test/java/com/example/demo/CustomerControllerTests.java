package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.vo.CustomerVo;
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
public class CustomerControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // 第五次迭代接口

    /**
     * 测试微信授权登录后，根据登录信息返回顾客id
     */
    @Test
    public void getCustomerId(){

        String url = "http://localhost:8080/customer/getCustomerId";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIwMDAwMDAwMDAwMDAwMDAwMDAwIiwiZXhwIjoxNjUzODcxNjg4LCJqdGkiOiJhMzZlOGI0Yi1iYjliLTQzOWItOWJjMy0zMjI2YmU2ZDJkNjgifQ.4ppfnLJy8CvEksUNeKPNpCVj8sTF1xsl_O7SzihWRBQ";


        JSONObject requestBody = new JSONObject();
        requestBody.put("cavatarUrl", "https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJm4OSrmhJ2NBoE37lL4FH5V5AXhWC7CojAbWdNialjo7FroXD2cBUVib6AhX7oX5VhHUyh1BpIr6JA/132");
        requestBody.put("cname", "测试");

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<Result> result = this.restTemplate.exchange(url, HttpMethod.POST, request, Result.class);
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

        String url = "http://localhost:8080/customer/findListByPage/{current}/{limit}";
        Map request = new HashMap();
        request.put("current", "1");
        request.put("limit", "5");
        Result result = this.restTemplate.getForObject(url, Result.class, request);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());//状态码为20000
    }
}
