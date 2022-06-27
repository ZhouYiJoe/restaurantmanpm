package com.example.demo;


import com.firstGroup.restaurant.RestaurantApplication;
import com.firstGroup.restaurant.common.Result;
import com.firstGroup.restaurant.model.Category;
import com.firstGroup.restaurant.model.vo.CategoryVo;
import com.firstGroup.restaurant.model.vo.CategoryVo1;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RestaurantApplication.class)
//@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // 第三次迭代

    /**
     * 测试分页查询分类，可指定排序方式、模糊查询关键字
     */
    @Test
    public void findPage(){
        String url = "http://localhost:8080/category/findPage?";
        String request = "pageId=1&pageSize=5&categoryType=0&sortColumn=cate_id&sortOrder=asc";
        Result result = this.restTemplate.getForObject(url+request, Result.class);
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }

    /**
     * 测试新增分类
     */
    @Test
    public void add() {
        CategoryVo request = new CategoryVo();
        request.setCateName("测试");
        request.setCateType(2);
        request.setCateDescription("测试");

        Result result = this.restTemplate.postForObject("http://localhost:8080/category", request, Result.class);
        TestCase.assertEquals("20000", result.getCode().toString());
        System.out.println(result);
    }

    /**
     * 测试更新分类
     */
    @Test
    public void update() {
        CategoryVo1 categoryVo1 = new CategoryVo1();
        categoryVo1.setCateId("1518398772298649602");
        categoryVo1.setCateName("测试更新");
        categoryVo1.setCateType(2);
        categoryVo1.setCateDescription("测试更新");
        this.restTemplate.put("http://localhost:8080/category", categoryVo1, Result.class);
    }

    /**
     * 测试根据id查询分类
     */
    @Test
    public void findById() {
        Result result = this.restTemplate.getForObject("http://localhost:8080/category/1518398772298649602", Result.class);
        TestCase.assertEquals("20000", result.getCode().toString());
        System.out.println(result);
    }

    /**
     * 测试根据id删除分类
     */
    @Test
    public void delete() {
        this.restTemplate.delete("http://localhost:8080/category/1518399419739811841");
    }


    // 第二次迭代

    /**
     *测试查询分页数据
     */
    @Test
    public void findListByPage(){
        Map<String, Object> request = new HashMap<>();//请求参数
        request.put("current", 1);
        request.put("limit", 5);

        Result result = this.restTemplate.getForObject("http://localhost:8080/category/findListByPage/{current}/{limit}",
                Result.class, request);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
        TestCase.assertNotNull(result.getData());
    }

    /**
     * 测试获取所有分类信息
     */
    @Test
    public void getAllInfo(){
        Result result = this.restTemplate.getForObject("http://localhost:8080/category/getAllInfo", Result.class,
                Result.class, null);//请求结果
        System.out.println(result);
        TestCase.assertEquals("20000", result.getCode().toString());
    }
}
