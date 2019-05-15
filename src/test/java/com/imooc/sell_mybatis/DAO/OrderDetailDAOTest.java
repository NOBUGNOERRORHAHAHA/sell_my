package com.imooc.sell_mybatis.DAO;

import com.imooc.sell_mybatis.dataobject.OrderDetail;
import com.imooc.sell_mybatis.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.math.BigDecimal;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDAOTest {

    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Test
    public void findByOrderIdTest() {
        List<OrderDetail>  orderDetailList = orderDetailDAO.findByOrderId("1");
        Assert.assertNotEquals(0,orderDetailList.size());
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("2");
        orderDetail.setOrderId("2");
        orderDetail.setProductId("2");
        orderDetail.setProductName("orange");
        orderDetail.setProductPrice(new BigDecimal(2.5));
        orderDetail.setProductQuantity(10);
        orderDetail.setProductIcon("sdfasdfa");
        int result = orderDetailDAO.save(orderDetail);
        Assert.assertNotEquals(0,result);
    }

}