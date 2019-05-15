package com.imooc.sell_mybatis.service.impl;

import com.imooc.sell_mybatis.dataobject.OrderDetail;
import com.imooc.sell_mybatis.dataobject.ProductInfo;
import com.imooc.sell_mybatis.dto.OrderDTO;
import com.imooc.sell_mybatis.enums.OrderStatusEnum;
import com.imooc.sell_mybatis.enums.PayStatusEnum;
import com.imooc.sell_mybatis.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ProductServiceImpl productService;

    private final String BUYER_OPENID = "1101110";

    private final String ORDER_ID = "1";
    @Test
    @Transactional
   // @Rollback(value=false)
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("liuting");
        orderDTO.setBuyerAddress("NAU");
        orderDTO.setBuyerPhone("15852901066");
        orderDTO.setBuyerOpenId(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail o1 = new OrderDetail();
        o1.setProductId("12345");
        o1.setProductQuantity(1);

        orderDetailList.add(o1);
        orderDTO.setOrderDetailList(orderDetailList);

        try {
            OrderDTO result = orderService.create(orderDTO);
            log.info("创建订单 result={}", result);
            for (OrderDetail o : result.getOrderDetailList()) {
                System.out.println(productService.findOne(o.getProductId()).getProductStock());
            }
            Assert.assertNotNull(result);
        } catch (SellException ex) {
            System.out.println("error!!!    "+ex.getMessage());
        }

    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("[查询单个订单] result={} ",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());

    }

    @Test
    public void findList() {
        List<OrderDTO> orderDTOList = orderService.findList(BUYER_OPENID);
        Assert.assertNotEquals(0, orderDTOList.size());
    }

    @Test
    @Transactional
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    @Transactional
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    @Transactional
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());

    }
}