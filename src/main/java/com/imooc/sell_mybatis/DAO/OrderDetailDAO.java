package com.imooc.sell_mybatis.DAO;

import com.imooc.sell_mybatis.dataobject.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDetailDAO {
    List<OrderDetail> findByOrderId (@Param("orderId") String orderId);

    int save(OrderDetail orderDetail);

}
