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
public class MenuOrderControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // 第二次迭代测试

    /**
     * 测试获取一个订单所包含的所有菜品
     */
    @Test
    public void findForOrder(){

        String url = "http://localhost:8080/menu-order/findForOrder/{oId}";
        Map request = new HashMap();
        request.put("oId", "2022031923294916248");
        Result result = this.restTemplate.getForObject(url, Result.class, request);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }
}
