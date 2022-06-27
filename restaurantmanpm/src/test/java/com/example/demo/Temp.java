package com.example.demo;

import com.firstGroup.restaurant.RestaurantApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//用@SpringBootTest注解指定启动类
@SpringBootTest(classes = RestaurantApplication.class)
public class Temp {
    @Test
    public void test() {

    }
}
