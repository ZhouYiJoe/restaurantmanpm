package com.firstGroup.restaurant.service.impl;

import com.firstGroup.restaurant.mapper.*;
import com.firstGroup.restaurant.model.*;
import com.firstGroup.restaurant.model.dto.AppUserDetails;
import com.firstGroup.restaurant.model.dto.UserType;
import com.firstGroup.restaurant.service.ISecurityService;
import com.firstGroup.restaurant.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements ISecurityService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;
    @Autowired
    private AdviceMapper adviceMapper;

    @Override
    public boolean isAccessible(Order order) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.CUSTOMER) {
            return userId.equals(order.getCId());
        }
        else if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(order.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Foodtable foodtable) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.CUSTOMER) {
            Customer customer = customerMapper.selectById(userId);
            return customer != null && customer.getFId().equals(foodtable.getFId());
        }
        else if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(foodtable.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Employee employee) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(employee.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Advice advice) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        Order order = orderMapper.selectById(advice.getOId());
        if (order == null) return false;
        if (userType == UserType.CUSTOMER) {
            Customer customer = customerMapper.selectById(userId);
            return customer != null  &&
                    customer.getCId().equals(order.getCId());
        } else if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(order.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Customer customer) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.CUSTOMER) {
            return userId.equals(customer.getCId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(PurchaseList purchaseList) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            PurchaseRecord purchaseRecord =
                    purchaseRecordMapper.selectById(purchaseList.getPrId());
            return admin != null && admin.getRId().equals(purchaseRecord.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Restaurant restaurant) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(restaurant.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Stock stock) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(stock.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Dish dish) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(dish.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(PurchaseRecord purchaseRecord) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        UserType userType = userDetails.getUserType();
        String userId = userDetails.getUsername();
        if (userType == UserType.ADMIN) {
            Admin admin = adminMapper.selectById(userId);
            return admin != null && admin.getRId().equals(purchaseRecord.getRId());
        }
        return true;
    }

    @Override
    public boolean isAccessible(Feedback feedback) {
        AppUserDetails userDetails = SpringSecurityUtil.getCurrentUserDetails();
        if (userDetails.getUserType() == UserType.SUPER_ADMIN) return true;
        String userId = userDetails.getUsername();
        Admin admin = adminMapper.selectById(userId);
        if (admin == null) return false;
        Advice advice = adviceMapper.selectById(feedback.getAdviceId());
        if (advice == null) return false;
        Order order = orderMapper.selectById(advice.getOId());
        if (order == null) return false;
        return admin.getRId().equals(order.getRId());
    }
}
