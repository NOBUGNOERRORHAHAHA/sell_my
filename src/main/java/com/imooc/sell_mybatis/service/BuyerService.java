package com.imooc.sell_mybatis.service;

import com.imooc.sell_mybatis.dto.OrderDTO;

public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消一个订单
    OrderDTO cancelOrder(String openid, String orderId);

}
