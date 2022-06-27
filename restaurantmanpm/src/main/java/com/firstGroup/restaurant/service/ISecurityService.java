package com.firstGroup.restaurant.service;

import com.firstGroup.restaurant.model.*;

/**
 * 判断当前用户是否能够访问某数据
 * 比如一个顾客不可以访问别的顾客的数据
 * 某个餐厅的管理员不可以访问别的餐厅的数据
 */
public interface ISecurityService {
    boolean isAccessible(Order order);

    boolean isAccessible(Foodtable foodtable);

    boolean isAccessible(Employee employee);

    boolean isAccessible(Advice advice);

    boolean isAccessible(Customer customer);

    boolean isAccessible(PurchaseList purchaseList);

    boolean isAccessible(Restaurant restaurant);

    boolean isAccessible(Stock stock);

    boolean isAccessible(Dish dish);

    boolean isAccessible(PurchaseRecord purchaseRecord);

    boolean isAccessible(Feedback feedback);
}
