package com.firstGroup.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firstGroup.restaurant.mapper.*;
import com.firstGroup.restaurant.model.*;
import com.firstGroup.restaurant.model.vo.OrderMenuVo;
import com.firstGroup.restaurant.model.vo.OrderVo;
import com.firstGroup.restaurant.model.vo.OrderVo1;
import com.firstGroup.restaurant.service.IOrderService;
import com.firstGroup.restaurant.utils.OrderNoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author firstGroup
 * @since 2022-03-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private MenuOrderMapper menuOrderMapper;

    @Autowired
    private DishMcMapper dishMcMapper;  //配料表mapper

    @Autowired
    private StockMapper stockMapper;    //库存表mapper

    @Autowired
    private DishMapper dishMapper;      //菜品表mapper

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;  //订单明细mapper

    @Autowired
    private CustomerMapper customerMapper;  //客户的mapper

    @Override
    public void findCustomerOrderByRestaurantId(Page<Order> pageParam, String customer_id, String r_id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("c_id", customer_id)
                .eq("r_id", r_id)
                .orderByDesc("create_time");
        baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    @Transactional
    public void rollbackOrder(String oId) {

        //删除与oId相关的MenuOrder数据
        LambdaQueryWrapper<MenuOrder> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(MenuOrder::getOId, oId);
        List<MenuOrder> menuOrderList = menuOrderMapper.selectList(wrapper1);
        menuOrderMapper.delete(wrapper1);
        //回退库存
        for (MenuOrder menuOrder : menuOrderList) {
            //修改库存信息
            String dId = menuOrder.getDId();    //菜品id
            Integer number = menuOrder.getNumber(); //菜品数量
            QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
            dishWrapper.eq("d_id", dId);
            Dish dish = dishMapper.selectOne(dishWrapper);
            String rId = dish.getRId();     //根据菜品id查询餐馆id

            QueryWrapper<DishMc> dishMcWrapper2 = new QueryWrapper<>();
            dishMcWrapper2.eq("d_id", dId);
            List<DishMc> dishMcList = dishMcMapper.selectList(dishMcWrapper2); //本菜品所有配料信息
            for (DishMc dishMc : dishMcList) {
                //查询此库存
                QueryWrapper<Stock> stockWrapper = new QueryWrapper<>();
                stockWrapper.eq("r_id", rId).eq("mc_id", dishMc.getMcId());
                Stock stock = stockMapper.selectOne(stockWrapper);
                BigDecimal consumedNum = dishMc.getNumber().multiply(new BigDecimal(number));
                stock.setSStock(stock.getSStock().add(consumedNum));    //恢复
                stockMapper.update(stock, stockWrapper);//更新库存
            }
        }
        //删除一条订单数据
        baseMapper.deleteById(oId);
        //删除订单明细
        orderDetailsMapper.deleteById(oId);
    }


    @Override
    public List<OrderVo1> findForCustomer(String customer_id) {
        List<OrderVo1> orderVo1s = new ArrayList<>();
        //查询所有订单
        LambdaQueryWrapper<Order> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Order::getCId, customer_id).orderByDesc(Order::getCreateTime);
        List<Order> orders = baseMapper.selectList(wrapper1);
        for (Order o : orders) {
            OrderVo1 orderVo1 = new OrderVo1();
            //查询订单明细
            LambdaQueryWrapper<OrderDetails> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(OrderDetails::getOId, o.getOId());
            OrderDetails details = orderDetailsMapper.selectOne(wrapper2);
            if (details != null) {
                BeanUtils.copyProperties(details, orderVo1);
            }
            BeanUtils.copyProperties(o, orderVo1);
            orderVo1s.add(orderVo1);
        }
        return orderVo1s;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)//修改隔离机制
    //实现乐观锁保证并发安全下单，失败尝试次数为3次
    public String createOrder(OrderVo orderVo) throws Exception {
        //添加一条订单数据
        Order order = new Order();
        order.setCId(orderVo.getCId());
        order.setOId(OrderNoUtils.getOrderNo());

        System.out.println(order.getOId());

        order.setOPrice(orderVo.getAllPrice());
        order.setOState(0);
        order.setRId(orderVo.getRId());
        baseMapper.insert(order);
        //添加一条订单明细数据
        OrderDetails details = new OrderDetails();
        details.setOId(order.getOId());
        details.setNote(orderVo.getNote());
        details.setNumber(orderVo.getDinnerNum());
        String dishId = orderVo.getOrderMenuVoList().get(0).getDId();   //获取订单第一个菜品作为预览数据
        Dish dish = dishMapper.selectById(dishId);
        details.setImgurl(dish.getDImgurl());
        details.setName(dish.getDName());
        orderDetailsMapper.insert(details);

        //插入MenuOrder数据
        String oId = order.getOId();
        for (OrderMenuVo orderMenuVo : orderVo.getOrderMenuVoList()) {
            String dId = orderMenuVo.getDId();    //菜品id
            Integer number = orderMenuVo.getNumber(); //菜品数量
            MenuOrder menuOrder = new MenuOrder();
            menuOrder.setOId(oId);
            menuOrder.setDId(dId);
            menuOrder.setNumber(number);
            menuOrderMapper.insert(menuOrder);

            //修改库存信息
            String rId = orderVo.getRId();     //根据菜品id查询餐馆id

            QueryWrapper<DishMc> dishMcWrapper2 = new QueryWrapper<>();
            dishMcWrapper2.eq("d_id", dId);
            List<DishMc> dishMcList = dishMcMapper.selectList(dishMcWrapper2); //本菜品所有配料信息
            for (DishMc dishMc : dishMcList) {
                //每个库存修改都尝试3次
                for (int tryTimes = 0; ; tryTimes++) {
                    //查询此库存
                    QueryWrapper<Stock> stockWrapper = new QueryWrapper<>();
                    stockWrapper.eq("r_id", rId).eq("mc_id", dishMc.getMcId());
                    Stock stock = stockMapper.selectOne(stockWrapper);
                    BigDecimal consumedNum = dishMc.getNumber().multiply(new BigDecimal(number));
                    //判断库存是否足够
                    if (consumedNum.compareTo(stock.getSStock()) >= 0) {
                        QueryWrapper<Dish> dishQueryWrapper = new QueryWrapper<>();
                        dishQueryWrapper.eq("d_id", orderMenuVo.getDId());
                        Dish dish1 = dishMapper.selectOne(dishQueryWrapper);
                        throw new Exception(dish1.getDName() + "库存不足！");
                    }
                    stock.setSStock(stock.getSStock().subtract(consumedNum));
                    int update = stockMapper.update(stock, stockWrapper);//更新库存
                    if (update == 0) { //第三次还是更新失败，则抛出异常
                        if (tryTimes == 2) {
                            throw new Exception("当前下单人数较多，请稍后再试！");
                        }
                    } else { //正常修改，则退出循环
                        break;
                    }
                }
            }
        }
        return order.getOId();
    }

    @Override
    public int updateData(Order order) {
        return baseMapper.updateById(order);
    }

    @Override
    public Order findById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public BigDecimal getSum(String restaurantId, Date from, Date to) {
        LambdaQueryWrapper<Order> cond = Wrappers.lambdaQuery();
        if (restaurantId != null)
            cond.eq(Order::getRId, restaurantId);
        if (from != null && to != null)
            cond.between(Order::getCreateTime, from, to);
        cond.eq(Order::getOState, 1)
                .select(Order::getOPrice);
        BigDecimal sales = BigDecimal.ZERO;
        for (Order order : baseMapper.selectList(cond)) {
            sales = sales.add(order.getOPrice());
        }
        return sales;
    }

    /**
     * @param restaurantId 如果为null，则查询所有餐厅中的数据
     * @param from         如果为null，则不限定日期范围，查询所有数据
     * @param to           如果为null，则不限定日期范围，查询所有数据
     */
    @Override
    public int getNum(String restaurantId, Date from, Date to) {
        LambdaQueryWrapper<Order> cond = new LambdaQueryWrapper<>();
        if (restaurantId != null)
            cond.eq(Order::getRId, restaurantId);
        if (from != null && to != null)
            cond.between(Order::getCreateTime, from, to);
        return baseMapper.selectCount(cond);
    }

    @Override
    public Object findPage(Integer pageId,
                           Integer pageSize,
                           String r_id,
                           String sortColumn,
                           String sortOrder,
                           String keyword,
                           Date from,
                           Date to) {
        QueryWrapper<Order> wrapper = Wrappers.query();
        wrapper.eq("r_id", r_id);
        if (keyword != null) {
            QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
            customerQueryWrapper.select("c_id");
            customerQueryWrapper.like("c_name", keyword);
            List<String> cIdList = new ArrayList<>();
            for(Customer cus : customerMapper.selectList(customerQueryWrapper)){
                cIdList.add(cus.getCId());
            }
            wrapper.in("c_id", cIdList);
        }
        if(sortColumn != null){
            if (sortOrder.equals("asc")) wrapper.orderByAsc(sortColumn);
            else if (sortOrder.equals("desc")) wrapper.orderByDesc(sortColumn);
            else wrapper.orderByDesc(sortColumn);
        }
        if (from != null && to != null)
            wrapper.between("create_time", from, to);

        return baseMapper.selectPage(new Page<>(pageId, pageSize), wrapper);
    }
}
