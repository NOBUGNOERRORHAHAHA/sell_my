package com.imooc.sell_mybatis.service.impl;

import com.imooc.sell_mybatis.dto.OrderDTO;
import com.imooc.sell_mybatis.enums.ResultEnum;
import com.imooc.sell_mybatis.exception.SellException;
import com.imooc.sell_mybatis.service.BuyerService;
import com.imooc.sell_mybatis.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;


    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
       return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查不到该订单, orderId = {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderDTO;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenId().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致，openid = {}, orderDTO = {}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);

        }
        return orderDTO;
    }
}
