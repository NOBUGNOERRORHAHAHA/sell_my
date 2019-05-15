package com.imooc.sell_mybatis.DAO;

import com.imooc.sell_mybatis.dataobject.OrderDetail;
import com.imooc.sell_mybatis.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDAOTest {
    @Autowired
    private OrderMasterDAO orderMasterDAO;
    @Test
    public void findByBuyerOpenidTest() {
        List<OrderMaster> orderMasters = orderMasterDAO.findByBuyerOpenid("xixixi");
        System.out.println(orderMasters.size());
        Assert.assertNotEquals(0,orderMasters.size());
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        OrderMaster orderMaster = new OrderMaster();

        orderMaster.setOrderId("2");
        orderMaster.setBuyerName("ZHANGSAN");
        orderMaster.setBuyerPhone("1582321");
        orderMaster.setBuyerAddress("BEIJING");
        orderMaster.setBuyerOpenId("100");
        orderMaster.setOrderAmount(new BigDecimal(23.9));
        int result = orderMasterDAO.save(orderMaster);
        Assert.assertNotEquals(0,result);
    }

    @Test
    public void findOneTest() throws Exception {
        OrderMaster orderMaster = orderMasterDAO.findOne("1");
        Assert.assertNotNull(orderMaster);
    }
}