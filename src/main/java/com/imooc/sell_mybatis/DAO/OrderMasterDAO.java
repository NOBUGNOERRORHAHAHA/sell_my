package com.imooc.sell_mybatis.DAO;

import com.imooc.sell_mybatis.dataobject.OrderMaster;
import com.imooc.sell_mybatis.dto.OrderDTO;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMasterDAO {
    List<OrderMaster> findByBuyerOpenid (@Param("buyerOpenid") String buyerOpenid);

    int save(OrderMaster orderMaster);

    OrderMaster findOne(@Param("orderId") String orderId);

    int updateStatus(OrderDTO orderDTO);

    int updatePayStatus(OrderDTO orderDTO);

    List<OrderMaster> findAll();


}
