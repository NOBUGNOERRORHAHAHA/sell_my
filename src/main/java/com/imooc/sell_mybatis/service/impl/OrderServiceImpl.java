package com.imooc.sell_mybatis.service.impl;

import com.imooc.sell_mybatis.DAO.OrderDetailDAO;
import com.imooc.sell_mybatis.DAO.OrderMasterDAO;
import com.imooc.sell_mybatis.DAO.ProductInfoDAO;
import com.imooc.sell_mybatis.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell_mybatis.dataobject.OrderDetail;
import com.imooc.sell_mybatis.dataobject.OrderMaster;
import com.imooc.sell_mybatis.dataobject.ProductInfo;
import com.imooc.sell_mybatis.dto.CartDTO;
import com.imooc.sell_mybatis.dto.OrderDTO;
import com.imooc.sell_mybatis.enums.OrderStatusEnum;
import com.imooc.sell_mybatis.enums.PayStatusEnum;
import com.imooc.sell_mybatis.enums.ResultEnum;
import com.imooc.sell_mybatis.exception.SellException;
import com.imooc.sell_mybatis.service.OrderService;
import com.imooc.sell_mybatis.service.ProductService;
import com.imooc.sell_mybatis.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.pattern.PathPattern;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Autowired
    private OrderMasterDAO orderMasterDAO;

    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) throws SellException {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList = new ArrayList<>();
        //1.查询商品（数量，价格）

        for (OrderDetail orderDetail:orderDTO.getOrderDetailList() ) {
           ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
           if (productInfo == null) {
               throw new SellException(ResultEnum.PRODUCT_NOT_EXIST );
           }
            //2.计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
           //订单详情入库
           orderDetail.setOrderId(KeyUtil.genUniqueKey());
           orderDetail.setDetailId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
           orderDetailDAO.save(orderDetail);

//            CartDTO  cartDTO  = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }

        //3.写入订单数据库(orderMaster和orderrDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDAO.save(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDAO.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailDAO.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDEATIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findList(String buyerOpenid) {
        List<OrderMaster> orderMasterList = orderMasterDAO.findByBuyerOpenid(buyerOpenid);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList);
        return orderDTOList;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确,orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus((byte)2);
        if (orderMasterDAO.updateStatus(orderDTO) <= 0) {
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情,orderDTO={}", orderDTO );
            throw new SellException(ResultEnum.ORDER_DELETE_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已支付，需要退款

        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单
        orderDTO.setOrderStatus((byte)1);
        if (orderMasterDAO.updateStatus(orderDTO) <= 0) {
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】 订单状态不正确, orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus((byte)1);
        if ( orderMasterDAO.updatePayStatus(orderDTO) <= 0) {
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findList() {
        List<OrderMaster> orderMasterList = orderMasterDAO.findAll();
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList);
        return orderDTOList;
    }
}
