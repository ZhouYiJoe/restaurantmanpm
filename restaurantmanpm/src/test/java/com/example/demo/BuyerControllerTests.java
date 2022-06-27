package com.example.demo;

import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
public class BuyerControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // 第一次迭代测试

    /**
     * 测试查询分页数据
     */
    @Test
    public void findListByPage(){

        String url = "http://localhost:8080/buyer/findListByPage/{current}/{limit}";
        Map request = new HashMap();
        request.put("current", 1);
        request.put("limit", 5);
        Result result = this.restTemplate.getForObject(url, Result.class, request);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());//状态码为20000
    }
}
